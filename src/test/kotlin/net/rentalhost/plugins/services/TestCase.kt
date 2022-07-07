package net.rentalhost.plugins.services

import com.intellij.codeInsight.intention.EmptyIntentionAction
import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.jetbrains.php.config.PhpLanguageLevel
import com.jetbrains.php.config.PhpProjectConfigurationFacade
import com.jetbrains.php.lang.inspections.PhpInspection
import java.io.File
import java.lang.reflect.InvocationTargetException
import java.util.function.Consumer

abstract class TestCase: BasePlatformTestCase() {
    @Throws(Exception::class)
    override fun setUp() {
        super.setUp()

        myFixture.testDataPath = File("src/test/resources").absolutePath
    }

    protected fun <T: PhpInspection?> testInspection(
        inspectionClass: Class<T>,
        phpSourceSubName: String? = null,
        inspectionSetup: Consumer<T>? = null,
        phpLanguageLevel: PhpLanguageLevel? = null,
        quickFixesEnabled: Boolean? = null
    ) {
        val phpSource = inspectionClass.name.substring(classBaseLength + 1).replace(".", "/")
        val phpSourceNamed = if (phpSourceSubName == null) phpSource else "$phpSource-$phpSourceSubName"

        val phpInspection: T = try {
            inspectionClass.getDeclaredConstructor().newInstance()
        }
        catch (e: InstantiationException) {
            throw RuntimeException(e)
        }
        catch (e: IllegalAccessException) {
            throw RuntimeException(e)
        }
        catch (e: InvocationTargetException) {
            throw RuntimeException(e)
        }
        catch (e: NoSuchMethodException) {
            throw RuntimeException(e)
        }

        inspectionSetup?.accept(phpInspection)

        val phpLanguageLevelDeclared = phpLanguageLevel ?: PhpLanguageLevel.PHP810

        PhpProjectConfigurationFacade.getInstance(project).languageLevel = phpLanguageLevelDeclared

        myFixture.enableInspections(phpInspection)
        myFixture.configureByFile("$phpSourceNamed.php")
        myFixture.testHighlighting(true, false, true)

        if (quickFixesEnabled != false) {
            val phpLanguageLevelSuffix =
                if (phpLanguageLevelDeclared == PhpLanguageLevel.PHP810) ""
                else ".php${phpLanguageLevelDeclared.presentableName.replace(".", "")}0"

            val inspectionQuickFixes = myFixture.getAllQuickFixes()
                .filter { it !is EmptyIntentionAction }

            if (inspectionQuickFixes.isNotEmpty()) {
                inspectionQuickFixes.forEach(Consumer { fix: IntentionAction? -> myFixture.launchAction(fix!!) })
                myFixture.checkResultByFile("$phpSourceNamed.fixed$phpLanguageLevelSuffix.php")
            }
        }
    }

    companion object {
        private const val classBaseLength = "net.rentalhost.plugins.php.hammer".length
    }
}
