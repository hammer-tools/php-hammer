package net.rentalhost.plugins.php.hammer.inspections.deadCode

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.impl.FunctionImpl
import com.jetbrains.php.lang.psi.elements.impl.PhpUseListImpl
import net.rentalhost.plugins.extensions.psi.accessVariables
import net.rentalhost.plugins.extensions.psi.declarationTextRange
import net.rentalhost.plugins.extensions.psi.getVariables
import net.rentalhost.plugins.services.ProblemsHolderService

class UnusedUseVariableInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object: PsiElementVisitor() {
        override fun visitElement(element: PsiElement) {
            if (element is PhpUseListImpl) {
                val useVariables = element.getVariables()

                if (useVariables.isNullOrEmpty())
                    return

                val useContext = element.context as FunctionImpl

                val functionVariables = useContext.accessVariables()
                    .map { it.variableName }
                    .distinct()

                for (useVariable in useVariables) {
                    if (!functionVariables.contains(useVariable.name)) {
                        ProblemsHolderService.registerProblem(
                            problemsHolder,
                            element,
                            useVariable.declarationTextRange(element),
                            "Unused variable declared in use()."
                        )
                    }
                }
            }
        }
    }
}
