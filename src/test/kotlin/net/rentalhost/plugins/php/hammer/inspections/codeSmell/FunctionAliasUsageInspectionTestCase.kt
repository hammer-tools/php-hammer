package net.rentalhost.plugins.php.hammer.inspections.codeSmell

import com.jetbrains.php.config.PhpLanguageLevel
import net.rentalhost.plugins.php.hammer.services.TestCase

class FunctionAliasUsageInspectionTestCase : TestCase() {
  fun testAll(): Unit = testInspection(FunctionAliasUsageInspection::class.java)

  fun testBefore710(): Unit = testInspection(
    FunctionAliasUsageInspection::class.java, "before710",
    phpLanguageLevel = PhpLanguageLevel.PHP530
  )
}
