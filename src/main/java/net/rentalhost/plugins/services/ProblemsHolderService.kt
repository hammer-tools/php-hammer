package net.rentalhost.plugins.services

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement

private fun applyTemplate(descriptionTemplate: String) =
    "[PHP Hammer] $descriptionTemplate"

object ProblemsHolderService {
    fun registerProblem(problemsHolder: ProblemsHolder, element: PsiElement, textRange: TextRange?, descriptionTemplate: String, localQuickFix: LocalQuickFix? = null): Unit =
        problemsHolder.registerProblem(element, textRange, applyTemplate(descriptionTemplate), localQuickFix)

    fun registerProblem(problemsHolder: ProblemsHolder, element: PsiElement, descriptionTemplate: String, localQuickFix: LocalQuickFix? = null): Unit =
        registerProblem(problemsHolder, element, null, descriptionTemplate, localQuickFix)
}
