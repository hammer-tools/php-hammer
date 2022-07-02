package net.rentalhost.plugins.services

import com.intellij.psi.PsiElement
import com.intellij.psi.codeStyle.CodeStyleManager
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.codeStyle.CodeStyleSettingsManager
import com.jetbrains.php.lang.formatter.ui.predefinedStyle.PSR12CodeStyle

object FormatterService {
    private val projectCodeStyle: CodeStyleSettings = CodeStyleSettingsManager().createSettings()

    init {
        PSR12CodeStyle().apply(projectCodeStyle)
    }

    fun applyDefaults(element: PsiElement) {
        val projectCodeStyleSettingsManager = CodeStyleSettingsManager.getInstance(element.project)
        projectCodeStyleSettingsManager.setTemporarySettings(projectCodeStyle)

        CodeStyleManager.getInstance(element.project).reformat(element)

        projectCodeStyleSettingsManager.dropTemporarySettings()
    }
}
