package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.refactoring.suggested.createSmartPointer
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.MethodReference
import com.jetbrains.php.lang.psi.elements.impl.ClassReferenceImpl
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.extensions.psi.isName
import net.rentalhost.plugins.php.hammer.services.FactoryService
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService

class ToStringSimplificationInspection : PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object : PhpElementVisitor() {
        override fun visitPhpMethodReference(element: MethodReference) {
            if (!element.isName("__tostring"))
                return

            val elementBase = element.firstPsiChild ?: return

            if (elementBase is ClassReferenceImpl &&
                elementBase.text.lowercase() == "parent"
            )
                return

            ProblemsHolderService.instance.registerProblem(
                problemsHolder,
                element,
                "call to __toString() can be simplified",
                QuickFixService.instance.simpleReplace(
                    "Replace with type cast (string)",
                    FactoryService.createTypeCastExpression(problemsHolder.project, "string", elementBase.text).createSmartPointer()
                )
            )
        }
    }
}
