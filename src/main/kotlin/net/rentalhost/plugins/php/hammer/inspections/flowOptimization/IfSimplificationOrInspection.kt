package net.rentalhost.plugins.php.hammer.inspections.flowOptimization

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiWhiteSpace
import com.intellij.refactoring.suggested.createSmartPointer
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.*
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.extensions.psi.getNextSiblingConditional
import net.rentalhost.plugins.php.hammer.extensions.psi.getSingleStatement
import net.rentalhost.plugins.php.hammer.extensions.psi.isOrSimplified
import net.rentalhost.plugins.php.hammer.extensions.psi.isTerminatingStatement
import net.rentalhost.plugins.php.hammer.services.FactoryService
import net.rentalhost.plugins.php.hammer.services.FormatterService
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService

class IfSimplificationOrInspection : PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object : PhpElementVisitor() {
        private fun visit(element: ControlStatement) {
            if (!element.isOrSimplified())
                return

            val elementNext = element.getNextSiblingConditional() ?: return

            if (!elementNext.isOrSimplified())
                return

            val elementNormalized = (element.statement ?: return).getSingleStatement() ?: return

            if (elementNormalized !is Statement ||
                !elementNormalized.isTerminatingStatement()
            )
                return

            val elementNextNormalized = (elementNext.statement ?: return).getSingleStatement() ?: return

            if (elementNextNormalized !is Statement ||
                !elementNextNormalized.isTerminatingStatement() ||
                FormatterService.normalize(elementNextNormalized) != FormatterService.normalize(elementNormalized)
            )
                return

            val elementPointer = element.createSmartPointer()
            val elementNextPointer = elementNext.createSmartPointer()

            ProblemsHolderService.instance.registerProblem(
                problemsHolder,
                element.firstChild,
                "subsequent condition can be merged with this",
                QuickFixService.instance.simpleInline("Simplify conditional with the subsequent") {
                    val elementLocal = elementPointer.element ?: return@simpleInline
                    val elementNextLocal = elementNextPointer.element ?: return@simpleInline

                    val elementLocalCondition = elementLocal.condition ?: return@simpleInline
                    val elementNextLocalCondition = elementNextLocal.condition ?: return@simpleInline

                    if (elementLocal is If &&
                        elementNextLocal is If &&
                        elementLocal.nextSibling is PsiWhiteSpace
                    ) {
                        elementLocal.nextSibling.delete()
                    }

                    val elementMergedCondition = FactoryService.createBinaryExpression(
                        problemsHolder.project,
                        "${elementLocalCondition.text}||${elementNextLocalCondition.text}"
                    )

                    if (elementNextLocal is ElseIf) {
                        // Eg. <keep: if()> elseif()
                        elementLocalCondition.replace(elementMergedCondition)
                        elementNextLocal.delete()
                    } else if (elementLocal !is ElseIf &&
                        elementNextLocal.parent is Else
                    ) {
                        // Eg. if() else <keep: if()>
                        elementNextLocalCondition.replace(elementMergedCondition)
                        elementLocal.replace(elementNextLocal)
                    } else {
                        // Eg. if() <keep: if()>
                        // Eg. elseif() <keep: elseif()>
                        elementNextLocalCondition.replace(elementMergedCondition)
                        elementLocal.delete()
                    }
                }
            )
        }

        override fun visitPhpElseIf(element: ElseIf) = visit(element)
        override fun visitPhpIf(element: If) = visit(element)
    }
}
