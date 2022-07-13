package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.impl.PhpShellCommandExpressionImpl
import net.rentalhost.plugins.services.ProblemsHolderService

class BacktickReplacementInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object: PsiElementVisitor() {
        override fun visitElement(element: PsiElement) {
            if (element is PhpShellCommandExpressionImpl) {
                ProblemsHolderService.registerProblem(
                    problemsHolder,
                    element,
                    "Backtick operator can be replaced by shell_exec()."
                )
            }
        }
    }
}
