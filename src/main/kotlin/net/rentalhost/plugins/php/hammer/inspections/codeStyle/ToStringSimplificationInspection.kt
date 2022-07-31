package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.MethodReference
import com.jetbrains.php.lang.psi.elements.impl.ClassReferenceImpl
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.extensions.psi.isName
import net.rentalhost.plugins.services.FactoryService
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.ProblemsHolderService

class ToStringSimplificationInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        override fun visitPhpMethodReference(element: MethodReference) {
            if (!element.isName("__tostring"))
                return

            val elementBase = element.firstPsiChild ?: return

            if (elementBase is ClassReferenceImpl &&
                elementBase.text.lowercase() == "parent")
                return

            ProblemsHolderService.registerProblem(
                problemsHolder,
                element,
                "call to __toString() can be simplified",
                LocalQuickFixService.SimpleReplaceQuickFix(
                    "Replace with type cast (string)",
                    FactoryService.createTypeCastExpression(problemsHolder.project, "string", elementBase.text)
                )
            )
        }
    }
}
