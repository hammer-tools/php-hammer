package net.rentalhost.plugins.php.hammer.inspections.codeSmell

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.createSmartPointer
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.FunctionReference
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.extensions.psi.getErrorControlOperator
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService

class FunctionErrorSilencedInspection : PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object : PhpElementVisitor() {
        override fun visitPhpFunctionCall(element: FunctionReference) {
            ProblemsHolderService.instance.registerProblem(
                problemsHolder,
                element.getErrorControlOperator() ?: return,
                "function call is using error control operator",
                QuickFixService.instance.simpleReplace(
                    "Remove the \"@\" operator",
                    element.parent.createSmartPointer(),
                    element.createSmartPointer()
                )
            )
        }
    }
}
