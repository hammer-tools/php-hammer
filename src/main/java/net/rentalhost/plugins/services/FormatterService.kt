package net.rentalhost.plugins.services

import com.intellij.psi.PsiElement
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.codeStyle.CodeStyleSettingsManager
import com.intellij.psi.impl.source.codeStyle.CodeFormatterFacade
import com.jetbrains.php.lang.formatter.ui.predefinedStyle.PSR12CodeStyle

object FormatterService {
    private val projectCodeStyle: CodeStyleSettings = CodeStyleSettingsManager().createSettings()

    init {
        PSR12CodeStyle().apply(projectCodeStyle)
    }

    @Synchronized
    fun applyDefaults(element: PsiElement) {
        val projectCodeStyleSettingsManager = CodeStyleSettingsManager.getInstance(element.project)
        projectCodeStyleSettingsManager.setTemporarySettings(projectCodeStyle)

        CodeFormatterFacade(projectCodeStyle, element.language).processElement(element.node)

        projectCodeStyleSettingsManager.dropTemporarySettings()
    }
}
