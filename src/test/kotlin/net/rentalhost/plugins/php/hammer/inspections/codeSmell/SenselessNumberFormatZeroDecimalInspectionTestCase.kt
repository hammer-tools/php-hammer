package net.rentalhost.plugins.php.hammer.inspections.codeSmell

import net.rentalhost.plugins.php.hammer.TestCase

class SenselessNumberFormatZeroDecimalInspectionTestCase : TestCase() {
    fun testAll(): Unit = testInspection(
        SenselessNumberFormatZeroDecimalInspection::class.java,
        listOf("default", "dummy/functions.php")
    )
}
