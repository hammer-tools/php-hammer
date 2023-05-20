package net.rentalhost.plugins.php.hammer.inspections.codeWarning

import net.rentalhost.plugins.php.hammer.services.TestCase

class MissingParentCallInspectionTestCase : TestCase() {
  fun testAll(): Unit = testInspection(MissingParentCallInspection::class.java)
}
