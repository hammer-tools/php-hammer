package net.rentalhost.plugins.php.hammer.inspections.codeSmell

import net.rentalhost.plugins.php.hammer.services.TestCase

class SenselessParentCallEmptyInspectionTestCase : TestCase() {
    fun testAll(): Unit = testInspection(SenselessParentCallEmptyInspection::class.java)
}
