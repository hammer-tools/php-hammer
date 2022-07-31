package net.rentalhost.plugins.services

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.util.Key
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.refactoring.suggested.endOffset
import com.intellij.refactoring.suggested.startOffset

private fun applyTemplate(descriptionTemplate: String) =
    "\uD83D\uDD28 PHP Hammer: $descriptionTemplate"

private val inspectionsCountKeys =
    Key<Boolean>("inspectionsCountedKey")

object ProblemsHolderService {
    private fun increaseInspections(element: PsiElement) {
        if (element.getUserData(inspectionsCountKeys) == null) {
            element.putUserData(inspectionsCountKeys, true)

            SettingsService.increaseInspections()
        }
    }

    fun registerProblem(
        problemsHolder: ProblemsHolder,
        element: PsiElement,
        textRange: TextRange?,
        descriptionTemplate: String,
        localQuickFix: LocalQuickFix? = null,
        problemHighlightType: ProblemHighlightType? = null
    ) {
        increaseInspections(element)

        problemsHolder.registerProblem(
            element, applyTemplate(descriptionTemplate),
            problemHighlightType ?: ProblemHighlightType.GENERIC_ERROR_OR_WARNING,
            textRange, localQuickFix
        )
    }

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

    fun registerProblem(
        problemsHolder: ProblemsHolder,
        elementBase: PsiElement,
        elementFrom: PsiElement,
        elementTo: PsiElement,
        descriptionTemplate: String,
        localQuickFix: LocalQuickFix? = null
    ): Unit = registerProblem(
        problemsHolder,
        elementBase,
        TextRange(elementFrom.startOffset - elementBase.startOffset, elementTo.endOffset - elementBase.startOffset),
        descriptionTemplate,
        localQuickFix
    )
}
