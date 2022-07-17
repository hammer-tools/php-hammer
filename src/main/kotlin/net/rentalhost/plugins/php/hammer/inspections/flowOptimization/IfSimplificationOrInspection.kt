package net.rentalhost.plugins.php.hammer.inspections.flowOptimization

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.*
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.extensions.psi.getNextSiblingConditional
import net.rentalhost.plugins.extensions.psi.isOrSimplified
import net.rentalhost.plugins.extensions.psi.isTerminatingStatement
import net.rentalhost.plugins.services.FormatterService
import net.rentalhost.plugins.services.ProblemsHolderService

class IfSimplificationOrInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object: PhpElementVisitor() {
        fun visit(element: ControlStatement) {
            if (!element.isOrSimplified())
                return

            val elementNext = element.getNextSiblingConditional() ?: return

            if (!elementNext.isOrSimplified())
                return

            val elementNormalized = FormatterService.normalize(problemsHolder.project, (element.statement as? GroupStatement ?: return))

            if (!elementNormalized.isTerminatingStatement())
                return

            val elementNextNormalized = FormatterService.normalize(problemsHolder.project, (elementNext.statement as? GroupStatement ?: return))

            if (!elementNextNormalized.isTerminatingStatement() ||
                elementNextNormalized.text != elementNormalized.text)
                return

            ProblemsHolderService.registerProblem(
                problemsHolder,
                element.firstChild,
                "Subsequent condition can be merged with this."
            )
        }

        override fun visitPhpElseIf(element: ElseIf) = visit(element)
        override fun visitPhpIf(element: If) = visit(element)
    }
}
