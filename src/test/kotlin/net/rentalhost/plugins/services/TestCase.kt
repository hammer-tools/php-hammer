package net.rentalhost.plugins.services

import com.intellij.codeInsight.intention.EmptyIntentionAction
import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.jetbrains.php.config.PhpLanguageLevel
import com.jetbrains.php.config.PhpProjectConfigurationFacade
import com.jetbrains.php.lang.inspections.PhpInspection
import java.io.File
import java.util.function.Consumer

abstract class TestCase: BasePlatformTestCase() {
    @Throws(Exception::class)
    override fun setUp() {
        super.setUp()

        myFixture.testDataPath = File("src/test/resources").absolutePath
    }

    protected fun <T: PhpInspection?> testInspection(
        inspectionClass: Class<T>,
        phpSourceSubName: String?,
        inspectionSetup: Consumer<T>? = null,
        phpLanguageLevel: PhpLanguageLevel? = null,
        quickFixesEnabled: Boolean? = null
    ): Unit = testInspection(inspectionClass, listOf(phpSourceSubName), inspectionSetup, phpLanguageLevel, quickFixesEnabled)

    protected fun <T: PhpInspection?> testInspection(
        inspectionClass: Class<T>,
        phpSourceSubNames: List<String?>? = null,
        inspectionSetup: Consumer<T>? = null,
        phpLanguageLevel: PhpLanguageLevel? = null,
        quickFixesEnabled: Boolean? = null
    ) {
        val phpSourceBase = inspectionClass.name.substring(classBaseLength + 1).replace(".", "/")
        val phpSourceSub = phpSourceSubNames?.first() ?: "default"

        val phpInspection: T = try {
            inspectionClass.getDeclaredConstructor().newInstance()
        }
        catch (e: Exception) {
            throw e
        }

        inspectionSetup?.accept(phpInspection)

        val phpLanguageLevelDeclared = phpLanguageLevel ?: PhpLanguageLevel.PHP810

        PhpProjectConfigurationFacade.getInstance(project).languageLevel = phpLanguageLevelDeclared

        myFixture.enableInspections(phpInspection)

        if (phpSourceSubNames != null && phpSourceSubNames.size > 1) {
            myFixture.configureByFiles(*phpSourceSubNames.drop(1).toTypedArray())
        }

        myFixture.testHighlighting(true, false, true, "$phpSourceBase/$phpSourceSub.php")

        if (quickFixesEnabled != false) {
            val phpLanguageLevelSuffix =
                if (phpLanguageLevelDeclared == PhpLanguageLevel.PHP810) ""
                else ".php${phpLanguageLevelDeclared.presentableName.replace(".", "")}0"

            val quickFixFile = "$phpSourceBase/$phpSourceSub.fixed$phpLanguageLevelSuffix.php"

            if (File("src/test/resources/$quickFixFile").exists()) {
                val inspectionQuickFixes = myFixture.getAllQuickFixes()
                    .filter { it !is EmptyIntentionAction }

                if (inspectionQuickFixes.isNotEmpty()) {
                    inspectionQuickFixes.forEach(Consumer { fix: IntentionAction? -> myFixture.launchAction(fix ?: return@Consumer) })

                    myFixture.checkResultByFile(quickFixFile)
                }
            }
        }
    }

    companion object {
        private const val classBaseLength = "net.rentalhost.plugins.php.hammer".length
    }
}
