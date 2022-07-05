package net.rentalhost.plugins.php.hammer.inspections.deadCode

import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.impl.FunctionImpl
import com.jetbrains.php.lang.psi.elements.impl.PhpUseListImpl
import net.rentalhost.plugins.extensions.psi.*
import net.rentalhost.plugins.services.ProblemsHolderService

class UnusedUseVariableReferenceInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object: PsiElementVisitor() {
        override fun visitElement(element: PsiElement) {
            if (element is PhpUseListImpl) {
                val useVariables = with(element.getVariables()) {
                    if (isNullOrEmpty())
                        return

                    filter { it.isRef() }
                }

                val useContext = element.context as FunctionImpl

                val functionVariables = useContext.accessVariables()
                    .filter {
                        it.access.isWrite ||
                        it.access.isReadRef
                    }
                    .map { it.variableName }
                    .distinct()

                for (useVariable in useVariables) {
                    if (!functionVariables.contains(useVariable.name)) {
                        ProblemsHolderService.registerProblem(
                            problemsHolder,
                            useVariable.getLeafRef() as PsiElement,
                            "Unused reference for variable declared in use().",
                            problemHighlightType = ProblemHighlightType.LIKE_UNUSED_SYMBOL
                        )
                    }
                }
            }
        }
    }
}
