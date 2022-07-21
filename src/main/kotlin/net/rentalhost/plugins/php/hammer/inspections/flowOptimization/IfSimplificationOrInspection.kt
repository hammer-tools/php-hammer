package net.rentalhost.plugins.php.hammer.inspections.flowOptimization

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiWhiteSpace
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.*
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.extensions.psi.getNextSiblingConditional
import net.rentalhost.plugins.extensions.psi.getSingleStatement
import net.rentalhost.plugins.extensions.psi.isOrSimplified
import net.rentalhost.plugins.extensions.psi.isTerminatingStatement
import net.rentalhost.plugins.services.FactoryService
import net.rentalhost.plugins.services.FormatterService
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.ProblemsHolderService

class IfSimplificationOrInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        private fun visit(element: ControlStatement) {
            if (!element.isOrSimplified())
                return

            val elementNext = element.getNextSiblingConditional() ?: return

            if (!elementNext.isOrSimplified())
                return

            val elementNormalized = (element.statement ?: return).getSingleStatement() ?: return

            if (elementNormalized !is Statement ||
                !elementNormalized.isTerminatingStatement())
                return

            val elementNextNormalized = (elementNext.statement ?: return).getSingleStatement() ?: return

            if (elementNextNormalized !is Statement ||
                !elementNextNormalized.isTerminatingStatement() ||
                FormatterService.normalizeText(elementNextNormalized) != FormatterService.normalizeText(elementNormalized))
                return

            ProblemsHolderService.registerProblem(
                problemsHolder,
                element.firstChild,
                "Subsequent condition can be merged with this.",
                LocalQuickFixService.SimpleInlineQuickFix("Simplify conditional with the subsequent") {
                    val elementCondition = element.condition ?: return@SimpleInlineQuickFix
                    val elementNextCondition = elementNext.condition ?: return@SimpleInlineQuickFix

                    if (element is If &&
                        elementNext is If &&
                        element.nextSibling is PsiWhiteSpace) {
                        element.nextSibling.delete()
                    }

                    val elementMergedCondition = FactoryService.createBinaryExpression(
                        problemsHolder.project,
                        "${elementCondition.text}||${elementNextCondition.text}"
                    )

                    if (elementNext is ElseIf) {
                        // Eg. <keep: if()> elseif()
                        elementCondition.replace(elementMergedCondition)
                        elementNext.delete()
                    }
                    else if (element !is ElseIf &&
                             elementNext.parent is Else) {
                        // Eg. if() else <keep: if()>
                        elementNextCondition.replace(elementMergedCondition)
                        element.replace(elementNext)
                    }
                    else {
                        // Eg. if() <keep: if()>
                        // Eg. elseif() <keep: elseif()>
                        elementNextCondition.replace(elementMergedCondition)
                        element.delete()
                    }
                }
            )
        }

        override fun visitPhpElseIf(element: ElseIf) = visit(element)
        override fun visitPhpIf(element: If) = visit(element)
    }
}
