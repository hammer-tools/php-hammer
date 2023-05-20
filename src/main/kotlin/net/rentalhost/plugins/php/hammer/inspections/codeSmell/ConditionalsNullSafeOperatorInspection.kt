package net.rentalhost.plugins.php.hammer.inspections.codeSmell

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.nextLeaf
import com.jetbrains.php.config.PhpLanguageLevel
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.*
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.extensions.psi.insertAfter
import net.rentalhost.plugins.php.hammer.extensions.psi.isExactly
import net.rentalhost.plugins.php.hammer.extensions.psi.isOperatorAnd
import net.rentalhost.plugins.php.hammer.services.FactoryService
import net.rentalhost.plugins.php.hammer.services.FormatterService
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService

class ConditionalsNullSafeOperatorInspection: PhpInspection() {
    private val matchClasses: Array<Class<out PhpReference>> =
        arrayOf(Variable::class.java, FieldReference::class.java, MethodReference::class.java)

    private fun normalizeArrow(element: PsiElement) = FormatterService.normalize(element) { itElement, _ ->
        return@normalize !(itElement.isExactly<LeafPsiElement>() &&
                           itElement.text == "?" &&
                           itElement.nextLeaf()?.text == "->")
    }

    private fun getAllNextAnd(expression: BinaryExpression): List<PsiElement> = with(mutableListOf<PsiElement>()) {
        var currentExpression: BinaryExpression = expression

        while (currentExpression.isOperatorAnd()) {
            val rightOperand = currentExpression.rightOperand

            if (rightOperand is FieldReference ||
                rightOperand is MethodReference) {
                add(rightOperand)
            }

            currentExpression = currentExpression.parent as? BinaryExpression ?: break
        }

        return this
    }

    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        fun visitOperand(operand: PsiElement?, isRightOperand: Boolean = false) {
            if (operand !is Variable &&
                operand !is FieldReference)
                return

            val operandParent =
                if (isRightOperand) operand.parent.parent as? BinaryExpression ?: return
                else operand.parent as? BinaryExpression ?: return

            if (!operandParent.isOperatorAnd())
                return

            val operandNormalized = normalizeArrow(operand)
            val operandNormalizedArrow = "$operandNormalized->"

            val operandSiblings = getAllNextAnd(operandParent)
            val operandSiblingsParent = PsiTreeUtil.findCommonParent(operand, *operandSiblings.toTypedArray())

            PsiTreeUtil.findChildrenOfAnyType(operandSiblingsParent, *matchClasses)
                .filter {
                    val siblingNormalized = normalizeArrow(it)

                    siblingNormalized == operandNormalized ||
                    siblingNormalized.startsWith(operandNormalizedArrow)
                }
                .map { PsiTreeUtil.skipParentsOfType(it, *matchClasses) }
                .forEach { if (it !is BinaryExpression || !it.isOperatorAnd()) return }

            val operandMatches = operandSiblings.filter { normalizeArrow(it).startsWith(operandNormalizedArrow) }

            if (operandMatches.isEmpty())
                return

            ProblemsHolderService.instance.registerProblem(
                problemsHolder,
                operandParent.operation ?: return,
                "this operator can be replaced by null-safe operator",
                QuickFixService.instance.simpleInline("Replace with null-safe operator") {
                    operandMatches.forEach { it ->
                        if (!it.isValid)
                            return@forEach

                        if (it !is FieldReference &&
                            it !is MethodReference)
                            return@forEach

                        PsiTreeUtil.findChildrenOfType(it, operand::class.java).firstOrNull {
                            val operandSimilarNormalized = normalizeArrow(it)

                            if (operandNormalized == operandSimilarNormalized) {
                                if ((it.nextLeaf() ?: return@firstOrNull true).text != "?") {
                                    it.insertAfter(FactoryService.createTernary(problemsHolder.project))
                                }

                                it.replace(operand.copy())

                                return@firstOrNull true
                            }

                            return@firstOrNull false
                        }

                        var itCurrent = it

                        if (itCurrent.parent == operand.parent) {
                            itCurrent = operand.parent.replace(itCurrent)
                        }

                        if (itCurrent.parent !is PhpElementWithCondition &&
                            itCurrent.parent !is BinaryExpression) {
                            itCurrent.replace(FactoryService.createTypeCastExpression(problemsHolder.project, "bool", itCurrent.text))
                        }
                    }

                    val operandFixParent = operand.parent

                    if (operandFixParent.isValid) {
                        if (isRightOperand) {
                            operandFixParent.replace(operandFixParent.firstChild)
                        }
                        else if (operandFixParent is BinaryExpression) {
                            val operandFixParentRight = operandFixParent.rightOperand

                            if (operandFixParentRight != null) {
                                operandFixParent.replace(operandFixParentRight)
                            }
                        }
                    }
                }
            )
        }

        override fun visitPhpBinaryExpression(expression: BinaryExpression) {
            if (!expression.isOperatorAnd())
                return

            visitOperand(expression.leftOperand)
            visitOperand(expression.rightOperand, true)
        }
    }

    override fun getMinimumSupportedLanguageLevel(): PhpLanguageLevel =
        PhpLanguageLevel.PHP800
}
