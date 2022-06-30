package net.rentalhost.plugins.services

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement

object ProblemsHolderService {
    fun registerProblem(
        problemsHolder: ProblemsHolder,
        element: PsiElement,
        descriptionTemplate: String,
        localQuickFix: LocalQuickFix? = null
    ) {
        problemsHolder.registerProblem(
            element,
            "[PHP Hammer] $descriptionTemplate",
            localQuickFix
        )
    }
}
