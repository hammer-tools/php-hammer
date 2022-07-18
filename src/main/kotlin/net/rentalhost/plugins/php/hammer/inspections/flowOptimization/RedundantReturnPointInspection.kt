package net.rentalhost.plugins.php.hammer.inspections.flowOptimization

import com.intellij.codeInspection.ProblemsHolder
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.PhpPsiElement
import com.jetbrains.php.lang.psi.elements.PhpReturn
import com.jetbrains.php.lang.psi.elements.impl.ControlStatementImpl
import com.jetbrains.php.lang.psi.elements.impl.PhpReturnImpl
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.services.ElementService
import net.rentalhost.plugins.services.FormatterService
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.ProblemsHolderService

class RedundantReturnPointInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        override fun visitPhpReturn(element: PhpReturn) {
            val elementConditionalStarter = ElementService.conditionalStarter(element.parent.parent as PhpPsiElement)

            if (elementConditionalStarter !is ControlStatementImpl)
                return

            val elementNext = elementConditionalStarter.nextPsiSibling

            if (elementNext !is PhpReturnImpl)
                return

            val elementReturn = FormatterService.normalize(problemsHolder.project, element)
            val elementNextReturn = FormatterService.normalize(problemsHolder.project, elementNext)

            if (elementReturn.text != elementNextReturn.text)
                return

            ProblemsHolderService.registerProblem(
                problemsHolder,
                element,
                "Redundant return point.",
                LocalQuickFixService.SimpleDeleteQuickFix("Drop this return point")
            )
        }
    }
}
