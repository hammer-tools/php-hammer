package net.rentalhost.plugins.php.hammer.inspections.codeSmell

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.impl.FunctionReferenceImpl
import net.rentalhost.plugins.extensions.psi.getErrorControlOperator
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.ProblemsHolderService

class FunctionErrorSilencedInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object: PsiElementVisitor() {
        override fun visitElement(element: PsiElement) {
            if (element is FunctionReferenceImpl) {
                val errorControlOperator = element.getErrorControlOperator() ?: return

                ProblemsHolderService.registerProblem(
                    problemsHolder,
                    errorControlOperator,
                    "Function call is using error control operator.",
                    LocalQuickFixService.SimpleReplaceQuickFix("Remove the \"@\" operator", element.parent, element)
                )
            }
        }
    }
}
