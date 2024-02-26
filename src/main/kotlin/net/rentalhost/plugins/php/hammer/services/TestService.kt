package net.rentalhost.plugins.php.hammer.services

import com.intellij.codeInsight.intention.EmptyIntentionAction
import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.jetbrains.php.config.PhpLanguageLevel
import com.jetbrains.php.config.PhpProjectConfigurationFacade
import com.jetbrains.php.lang.inspections.PhpInspection
import java.io.File
import java.util.function.Consumer

abstract class TestCase : BasePlatformTestCase() {
    @Throws(Exception::class)
    override fun setUp() {
        super.setUp()

        myFixture.testDataPath = File("src/test/resources").absolutePath
    }

    protected fun <T : PhpInspection?> testInspection(
        inspectionClass: Class<T>,
        phpSourceName: String,
        phpSourceSubName: List<String>,
        inspectionSetup: Consumer<T>? = null,
        phpLanguageLevel: PhpLanguageLevel? = null,
        quickFixesEnabled: Boolean? = null
    ): Unit = testInspection(
        inspectionClass,
        listOf(phpSourceName, *phpSourceSubName.toTypedArray()),
        inspectionSetup,
        phpLanguageLevel,
        quickFixesEnabled
    )

    protected fun <T : PhpInspection?> testInspection(
        inspectionClass: Class<T>,
        phpSourceSubName: String?,
        inspectionSetup: Consumer<T>? = null,
        phpLanguageLevel: PhpLanguageLevel? = null
    ): Unit = testInspection(inspectionClass, listOf(phpSourceSubName), inspectionSetup, phpLanguageLevel)

    protected fun <T : PhpInspection?> testInspection(
        inspectionClass: Class<T>,
        phpSourceSubNames: List<String?>? = null,
        inspectionSetup: Consumer<T>? = null,
        phpLanguageLevel: PhpLanguageLevel? = null,
        quickFixesEnabled: Boolean? = null
    ) {
        val phpSourceBase = inspectionClass.name.substringAfter(".hammer.").replace(".", "/")
        val phpSourceSub = phpSourceSubNames?.first() ?: "default"

        val phpInspection: T = try {
            inspectionClass.getDeclaredConstructor().newInstance()
        } catch (e: Exception) {
            throw e
        }

        inspectionSetup?.accept(phpInspection)

        val phpLanguageLevelDeclared = phpLanguageLevel ?: PhpLanguageLevel.PHP830

        PhpProjectConfigurationFacade.getInstance(project).languageLevel = phpLanguageLevelDeclared

        myFixture.enableInspections(phpInspection)

        if (phpSourceSubNames != null && phpSourceSubNames.size > 1) {
            myFixture.configureByFiles(*phpSourceSubNames.drop(1).toTypedArray())
        }

        myFixture.testHighlighting(true, false, true, "$phpSourceBase/$phpSourceSub.php")

        if (quickFixesEnabled != false) {
            val phpLanguageLevelSuffix =
                if (phpLanguageLevelDeclared == PhpLanguageLevel.PHP830) ""
                else ".php${phpLanguageLevelDeclared.presentableName.replace(".", "")}0"

            val quickFixFile = "$phpSourceBase/$phpSourceSub.fixed$phpLanguageLevelSuffix.php"

            if (File("src/test/resources/$quickFixFile").exists()) {
                var inspectionQuickFixed = 0

                while (inspectionQuickFixed < 100) {
                    val inspectionQuickFixedBefore = inspectionQuickFixed

                    myFixture.getAllQuickFixes()
                        .filter { it !is EmptyIntentionAction }
                        .forEach(Consumer { fix: IntentionAction? ->
                            try {
                                myFixture.launchAction(fix ?: return@Consumer)
                                inspectionQuickFixed++
                            } catch (error: AssertionError) {
                                if (error.message?.contains("hasn't executed") == false) {
                                    throw error
                                }
                            }
                        })

                    if (inspectionQuickFixedBefore == inspectionQuickFixed) break
                }

                if (inspectionQuickFixed > 0) {
                    myFixture.checkResultByFile(quickFixFile)
                }
            }
        }
    }
}
