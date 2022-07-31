package net.rentalhost.plugins.php.hammer.inspections.codeSmell

import com.jetbrains.php.config.PhpLanguageLevel
import net.rentalhost.plugins.php.hammer.inspections.codeStyle.ParameterDefaultsNullInspection
import net.rentalhost.plugins.services.TestCase

class FunctionAliasUsageInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(FunctionAliasUsageInspection::class.java)

    fun testBefore710(): Unit = testInspection(ParameterDefaultsNullInspection::class.java, phpLanguageLevel = PhpLanguageLevel.PHP530)
}
