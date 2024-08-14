package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.php.hammer.TestCase

class StringSimplificationInspectionTestCase : TestCase() {
    fun testAll(): Unit = testInspection(StringSimplificationInspection::class.java)
}
