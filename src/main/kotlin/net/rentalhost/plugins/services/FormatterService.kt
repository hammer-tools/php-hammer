package net.rentalhost.plugins.services

import com.intellij.openapi.project.Project
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.codeStyle.CodeStyleSettingsManager
import com.intellij.psi.impl.source.codeStyle.CodeFormatterFacade
import com.jetbrains.php.lang.formatter.ui.predefinedStyle.PSR12CodeStyle
import com.jetbrains.php.lang.psi.PhpPsiElementFactory
import net.rentalhost.plugins.extensions.simplifyWhitespace

object FormatterService {
    private val projectCodeStyle: CodeStyleSettings = CodeStyleSettingsManager().createSettings()

    init {
        PSR12CodeStyle().apply(projectCodeStyle)
    }

    fun normalize(project: Project, statement: String): String {
        val statementElement = PhpPsiElementFactory.createStatement(project, statement.simplifyWhitespace())
        val projectCodeStyleSettingsManager = CodeStyleSettingsManager.getInstance(statementElement.project)

        synchronized(projectCodeStyleSettingsManager) {
            projectCodeStyleSettingsManager.setTemporarySettings(projectCodeStyle)

            CodeFormatterFacade(projectCodeStyle, statementElement.language).processElement(statementElement.node)

            projectCodeStyleSettingsManager.dropTemporarySettings()
        }

        return statementElement.text
    }
}
