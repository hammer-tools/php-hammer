package net.rentalhost.plugins.php.hammer.inspections.flowOptimization

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiWhiteSpace
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.*
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.hammer.extensions.psi.getNextSiblingConditional
import net.rentalhost.plugins.hammer.extensions.psi.getSingleStatement
import net.rentalhost.plugins.hammer.extensions.psi.isOrSimplified
import net.rentalhost.plugins.hammer.extensions.psi.isTerminatingStatement
import net.rentalhost.plugins.hammer.services.FactoryService
import net.rentalhost.plugins.hammer.services.FormatterService
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService

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
                FormatterService.normalize(elementNextNormalized) != FormatterService.normalize(elementNormalized))
                return

            ProblemsHolderService.instance.registerProblem(
                problemsHolder,
                element.firstChild,
                "subsequent condition can be merged with this",
                QuickFixService.instance.simpleInline("Simplify conditional with the subsequent") {
                    val elementCondition = element.condition ?: return@simpleInline
                    val elementNextCondition = elementNext.condition ?: return@simpleInline

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
