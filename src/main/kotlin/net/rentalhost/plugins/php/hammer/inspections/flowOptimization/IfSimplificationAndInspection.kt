package net.rentalhost.plugins.php.hammer.inspections.flowOptimization

import com.intellij.codeInspection.ProblemsHolder
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.ControlStatement
import com.jetbrains.php.lang.psi.elements.Else
import com.jetbrains.php.lang.psi.elements.ElseIf
import com.jetbrains.php.lang.psi.elements.If
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.extensions.psi.getSingleStatement
import net.rentalhost.plugins.extensions.psi.isAndSimplified
import net.rentalhost.plugins.services.FactoryService
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.ProblemsHolderService

class IfSimplificationAndInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        private fun visit(element: ControlStatement) {
            if (element is If) {
                if (element.elseBranch != null ||
                    element.elseIfBranches.isNotEmpty())
                    return
            }

            if (!element.isAndSimplified())
                return

            val elementStatement = element.statement ?: return
            val elementChild = elementStatement.getSingleStatement()

            if (elementChild !is If ||
                elementChild.elseBranch != null ||
                elementChild.elseIfBranches.isNotEmpty() ||
                !elementChild.isAndSimplified())
                return

            val elementNext = element.nextPsiSibling

            if (elementNext != null &&
                elementNext.parent === element.parent) {
                if (elementNext is ElseIf ||
                    elementNext is Else)
                    return
            }

            ProblemsHolderService.registerProblem(
                problemsHolder,
                element.firstChild,
                "nested condition can be merged with this",
                LocalQuickFixService.SimpleInlineQuickFix("Nested condition can be merged with this.") {
                    val elementCondition = element.condition ?: return@SimpleInlineQuickFix
                    val elementChildCondition = elementChild.condition ?: return@SimpleInlineQuickFix
                    val elementChildStatement = elementChild.statement ?: return@SimpleInlineQuickFix

                    elementCondition.replace(
                        FactoryService.createBinaryExpression(
                            problemsHolder.project,
                            "${elementCondition.text}&&${elementChildCondition.text}"
                        )
                    )

                    elementStatement.replace(elementChildStatement)
                }
            )
        }

        override fun visitPhpElseIf(element: ElseIf) = visit(element)
        override fun visitPhpIf(element: If) = visit(element)
    }
}
