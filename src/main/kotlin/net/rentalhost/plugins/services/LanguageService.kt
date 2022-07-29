package net.rentalhost.plugins.services

import com.intellij.openapi.project.Project
import com.jetbrains.php.config.PhpLanguageFeature
import com.jetbrains.php.config.PhpProjectConfigurationFacade

object LanguageService {
    private val namespacedClassnameRegex: Regex = Regex("\\\\?\\w+(?:\\\\[A-Z][A-Za-z\\d]*)+")

    fun hasFeature(project: Project, languageFeature: PhpLanguageFeature): Boolean =
        PhpProjectConfigurationFacade.getInstance(project).languageLevel.hasFeature(languageFeature)

    fun isNamespacedClassname(string: String) =
        namespacedClassnameRegex.matches(string)
}
