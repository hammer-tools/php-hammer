package net.rentalhost.plugins.services

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.codeStyle.CodeStyleSettingsManager
import com.intellij.psi.impl.source.codeStyle.CodeFormatterFacade
import com.jetbrains.php.lang.formatter.ui.predefinedStyle.PSR12CodeStyle
import com.jetbrains.php.lang.psi.PhpPsiElementFactory
import com.jetbrains.php.lang.psi.elements.If
import com.jetbrains.php.lang.psi.elements.Statement

object FormatterService {
    private val projectCodeStyle: CodeStyleSettings = CodeStyleSettingsManager().createSettings()

    init {
        PSR12CodeStyle().apply(projectCodeStyle)
    }

    fun normalize(project: Project, element: PsiElement): Statement {
        val elementCopy = element.copy()

        TreeService.getChildren(elementCopy, PsiComment::class)
            .forEach { it.delete() }

        TreeService.getChildren(elementCopy, PsiWhiteSpace::class)
            .forEach { it.replace(FactoryService.createWhiteSpace(project)) }

        val elementStatement = PhpPsiElementFactory.createStatement(project, "if(1)${elementCopy.text}") as If

        with(CodeStyleSettingsManager.getInstance(elementStatement.project)) {
            @Suppress("TestOnlyProblems")
            synchronized(this) {
                setTemporarySettings(projectCodeStyle)
                CodeFormatterFacade(projectCodeStyle, elementStatement.language).processElement(elementStatement.node)
                dropTemporarySettings()
            }
        }

        return elementStatement.statement!!
    }
}
