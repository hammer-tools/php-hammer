package net.rentalhost.plugins.php.hammer.inspections.codeSmell

import net.rentalhost.plugins.php.hammer.services.TestCase

class SenselessArrayUnpackingInspectionTestCase : TestCase() {
  fun testAll(): Unit = testInspection(SenselessArrayUnpackingInspection::class.java, listOf("default", "dummy/Native.php"))
}
