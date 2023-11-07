package net.rentalhost.plugins.php.hammer.inspections.codeWarning

import net.rentalhost.plugins.php.hammer.services.TestCase

class DangerousExtractInspectionTestCase : TestCase() {
  fun testAll(): Unit = testInspection(
    DangerousExtractInspection::class.java,
    listOf("default", "dummy/functions.php")
  )
}
