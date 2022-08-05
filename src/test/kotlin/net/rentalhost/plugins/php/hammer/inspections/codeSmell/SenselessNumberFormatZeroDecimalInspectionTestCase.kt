package net.rentalhost.plugins.php.hammer.inspections.codeSmell

import net.rentalhost.plugins.hammer.services.TestCase

class SenselessNumberFormatZeroDecimalInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(SenselessNumberFormatZeroDecimalInspection::class.java)
}
