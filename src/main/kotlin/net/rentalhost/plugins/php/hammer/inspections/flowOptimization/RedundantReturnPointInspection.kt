package net.rentalhost.plugins.php.hammer.inspections.flowOptimization

import com.intellij.codeInspection.ProblemsHolder
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.PhpPsiElement
import com.jetbrains.php.lang.psi.elements.PhpReturn
import com.jetbrains.php.lang.psi.elements.impl.ControlStatementImpl
import com.jetbrains.php.lang.psi.elements.impl.PhpReturnImpl
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.services.ElementService
import net.rentalhost.plugins.php.hammer.services.FormatterService
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService

class RedundantReturnPointInspection : PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object : PhpElementVisitor() {
        override fun visitPhpReturn(element: PhpReturn) {
            val elementConditionalStarter = ElementService.conditionalStarter(element.parent.parent as PhpPsiElement)

            if (elementConditionalStarter !is ControlStatementImpl)
                return

            val elementNext = elementConditionalStarter.nextPsiSibling

            if (elementNext !is PhpReturnImpl)
                return

            val elementReturn = FormatterService.normalize(element)
            val elementNextReturn = FormatterService.normalize(elementNext)

            if (elementReturn != elementNextReturn)
                return

            ProblemsHolderService.instance.registerProblem(
                problemsHolder,
                element,
                "redundant return point",
                QuickFixService.instance.simpleDelete("Drop this return point")
            )
        }
    }
}
