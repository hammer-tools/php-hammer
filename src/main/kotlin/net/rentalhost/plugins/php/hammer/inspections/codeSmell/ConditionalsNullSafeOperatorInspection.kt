package net.rentalhost.plugins.php.hammer.inspections.codeSmell

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.psi.util.nextLeaf
import com.jetbrains.php.config.PhpLanguageLevel
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.BinaryExpression
import com.jetbrains.php.lang.psi.elements.FieldReference
import com.jetbrains.php.lang.psi.elements.MethodReference
import com.jetbrains.php.lang.psi.elements.Variable
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.hammer.extensions.psi.isExactly
import net.rentalhost.plugins.hammer.extensions.psi.isOperatorAnd
import net.rentalhost.plugins.hammer.services.FormatterService
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService

class ConditionalsNullSafeOperatorInspection: PhpInspection() {
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

            val operandNormalized = "${normalizeArrow(operand)}->"
            val operandMatches = getAllNextAnd(operandParent).filter {
                (it is FieldReference || it is MethodReference) &&
                normalizeArrow(it).startsWith(operandNormalized)
            }

            if (operandMatches.isEmpty())
                return

            ProblemsHolderService.instance.registerProblem(
                problemsHolder,
                operandParent.operation ?: return,
                "this operator can be replaced by null-safe operator"
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
