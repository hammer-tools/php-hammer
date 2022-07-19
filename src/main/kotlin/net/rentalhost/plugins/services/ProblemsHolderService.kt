package net.rentalhost.plugins.services

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement

private fun applyTemplate(descriptionTemplate: String) =
    "\uD83D\uDD28 PHP Hammer: $descriptionTemplate"

object ProblemsHolderService {
    fun registerProblem(
        problemsHolder: ProblemsHolder,
        element: PsiElement,
        textRange: TextRange?,
        descriptionTemplate: String,
        localQuickFix: LocalQuickFix? = null,
        problemHighlightType: ProblemHighlightType? = null
    ): Unit = problemsHolder.registerProblem(
        element, applyTemplate(descriptionTemplate),
        problemHighlightType ?: ProblemHighlightType.GENERIC_ERROR_OR_WARNING,
        textRange, localQuickFix
    )

    fun registerProblem(
        problemsHolder: ProblemsHolder,
        element: PsiElement,
        descriptionTemplate: String,
        localQuickFix: LocalQuickFix? = null,
        problemHighlightType: ProblemHighlightType? = null
    ): Unit = registerProblem(
        problemsHolder,
        element,
        null,
        descriptionTemplate,
        localQuickFix,
        problemHighlightType
    )
}
