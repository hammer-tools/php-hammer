package net.rentalhost.plugins.services

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

    protected fun testInspection(
        inspectionClass: Class<out PhpInspection?>,
        phpSourceSubName: String? = null
    ) {
        val phpSource = inspectionClass.name.substring(classBaseLength + 1).replace(".", "/")
        val phpSourceNamed = if (phpSourceSubName == null) phpSource else "$phpSource-$phpSourceSubName"

        val phpInspection: PhpInspection? = try {
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

        PhpProjectConfigurationFacade.getInstance(project).languageLevel = PhpLanguageLevel.PHP800

        myFixture.enableInspections(phpInspection)
        myFixture.configureByFile("$phpSourceNamed.php")
        myFixture.testHighlighting(true, false, true)

        val phpSourceFixedFile = File("src/test/resources/$phpSourceNamed.fixed.php")

        if (phpSourceFixedFile.exists()) {
            val inspectionQuickFixes = myFixture.getAllQuickFixes()

            if (inspectionQuickFixes.isNotEmpty()) {
                inspectionQuickFixes.forEach(Consumer { fix: IntentionAction? -> myFixture.launchAction(fix!!) })
                myFixture.checkResultByFile("$phpSourceNamed.fixed.php")
            }
        }
    }

    companion object {
        private const val classBaseLength = "net.rentalhost.plugins.php.hammer".length
    }
}
