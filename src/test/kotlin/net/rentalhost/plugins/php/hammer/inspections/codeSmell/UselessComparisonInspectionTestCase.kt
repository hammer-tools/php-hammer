package net.rentalhost.plugins.php.hammer.inspections.codeSmell

import net.rentalhost.plugins.php.hammer.services.TestCase

class UselessComparisonInspectionTestCase : TestCase() {
  fun testAllIs(): Unit = testInspection(
    UselessComparisonInspection::class.java,
    listOf("allIs", "dummy/Native.php", "dummy/functions.php")
  )

  fun testAllInstanceOf(): Unit = testInspection(
    UselessComparisonInspection::class.java,
    listOf("allInstanceOf", "dummy/Native.php", "dummy/functions.php")
  )
}
