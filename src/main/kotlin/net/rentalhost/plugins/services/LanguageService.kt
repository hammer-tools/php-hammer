package net.rentalhost.plugins.services

import com.intellij.openapi.project.Project
import com.jetbrains.php.config.PhpLanguageFeature
import com.jetbrains.php.config.PhpLanguageLevel
import com.jetbrains.php.config.PhpProjectConfigurationFacade

object LanguageService {
    private val qualifiedClassname: Regex = Regex("(?:\\\\?[a-z][a-z_\\d]*)?(?:\\\\[a-z][a-z_\\d]*)+", RegexOption.IGNORE_CASE)

    fun hasFeature(project: Project, languageFeature: PhpLanguageFeature): Boolean =
        PhpProjectConfigurationFacade.getInstance(project).languageLevel.hasFeature(languageFeature)

    fun atLeast(project: Project, languageLevel: PhpLanguageLevel): Boolean =
        PhpProjectConfigurationFacade.getInstance(project).languageLevel.isAtLeast(languageLevel)

    fun isQualifiedClassname(string: String): Boolean =
        qualifiedClassname.matches(string)
}
