package net.rentalhost.plugins.php.hammer.services

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.util.Key
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.util.endOffset
import com.intellij.psi.util.startOffset

private val inspectionsCountKeys =
    Key<Boolean>("inspectionsCountedKey")

class ProblemsHolderService(private val projectService: ProjectService) {
    companion object {
        val instance: ProblemsHolderService = ProblemsHolderService(ProjectService.instance)
    }

    private fun increaseInspections(element: PsiElement) {
        if (element.getUserData(inspectionsCountKeys) == null) {
            element.putUserData(inspectionsCountKeys, true)

            projectService.settings.increaseInspections()
        }
    }

    fun registerProblem(
        problemsHolder: ProblemsHolder,
        element: PsiElement,
        textRange: TextRange?,
        descriptionTemplate: String,
        localQuickFix: LocalQuickFix? = null,
        problemHighlightType: ProblemHighlightType? = null
    ) = registerProblem(
        problemsHolder,
        element,
        textRange,
        descriptionTemplate,
        if (localQuickFix == null) emptyList()
        else listOf(localQuickFix),
        problemHighlightType
    )

    fun registerProblem(
        problemsHolder: ProblemsHolder,
        element: PsiElement,
        textRange: TextRange?,
        descriptionTemplate: String,
        localQuickFixes: List<LocalQuickFix>,
        problemHighlightType: ProblemHighlightType? = null
    ) {
        increaseInspections(element)

        val description = "\uD83D\uDD28 ${projectService.name}: $descriptionTemplate."
        val highlightType = problemHighlightType ?: ProblemHighlightType.GENERIC_ERROR_OR_WARNING

        if (localQuickFixes.isEmpty()) {
            problemsHolder.registerProblem(element, description, highlightType, textRange)
            return
        }

        problemsHolder.registerProblem(element, description, highlightType, textRange, *localQuickFixes.toTypedArray())
    }

    fun registerProblem(
        problemsHolder: ProblemsHolder,
        element: PsiElement,
        descriptionTemplate: String,
        localQuickFix: LocalQuickFix? = null,
        problemHighlightType: ProblemHighlightType? = null
    ): Unit = registerProblem(problemsHolder, element, null, descriptionTemplate, localQuickFix, problemHighlightType)

    fun registerProblem(
        problemsHolder: ProblemsHolder,
        element: PsiElement,
        descriptionTemplate: String,
        localQuickFixes: List<LocalQuickFix>,
        problemHighlightType: ProblemHighlightType? = null
    ): Unit = registerProblem(problemsHolder, element, null, descriptionTemplate, localQuickFixes, problemHighlightType)

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
