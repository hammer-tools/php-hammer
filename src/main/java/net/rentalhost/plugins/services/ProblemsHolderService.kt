package net.rentalhost.plugins.services

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.refactoring.suggested.endOffset
import com.intellij.refactoring.suggested.startOffset

object ProblemsHolderService {
    fun registerProblem(
        problemsHolder: ProblemsHolder,
        element: PsiElement,
        descriptionTemplate: String,
        localQuickFix: LocalQuickFix? = null
    ) {
        problemsHolder.registerProblem(element, applyTemplate(descriptionTemplate), localQuickFix)
    }

    fun registerProblem(
        problemsHolder: ProblemsHolder,
        element: PsiElement,
        elementFirst: PsiElement,
        elementLast: PsiElement,
        descriptionTemplate: String,
        localQuickFix: LocalQuickFix? = null
    ) {
        problemsHolder.registerProblem(
            element,
            TextRange(
                elementFirst.startOffset - element.startOffset,
                elementLast.endOffset - element.startOffset
            ),
            applyTemplate(descriptionTemplate),
            localQuickFix
        )
    }

    private fun applyTemplate(descriptionTemplate: String) = "[PHP Hammer] $descriptionTemplate"
}
