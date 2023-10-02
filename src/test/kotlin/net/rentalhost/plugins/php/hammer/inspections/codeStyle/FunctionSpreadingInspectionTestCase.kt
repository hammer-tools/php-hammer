package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.jetbrains.php.config.PhpLanguageLevel
import net.rentalhost.plugins.php.hammer.services.TestCase

class FunctionSpreadingInspectionTestCase : TestCase() {
  fun testAll(): Unit = testInspection(FunctionSpreadingInspection::class.java, listOf("default", "dummy/Native.php"))

  fun testAllBefore810(): Unit =
    testInspection(
      FunctionSpreadingInspection::class.java,
      listOf("spreadingBefore810", "dummy/Native.php"),
      { },
      PhpLanguageLevel.PHP800
    )
}
