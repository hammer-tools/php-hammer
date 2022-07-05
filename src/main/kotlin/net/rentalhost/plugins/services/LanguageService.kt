package net.rentalhost.plugins.services

import com.intellij.openapi.project.Project
import com.jetbrains.php.config.PhpLanguageFeature
import com.jetbrains.php.config.PhpProjectConfigurationFacade

object LanguageService {
    fun hasFeature(project: Project, languageFeature: PhpLanguageFeature): Boolean =
        PhpProjectConfigurationFacade.getInstance(project).languageLevel.hasFeature(languageFeature)
}
