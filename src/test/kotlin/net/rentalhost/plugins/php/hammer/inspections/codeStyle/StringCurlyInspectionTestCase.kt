package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.php.hammer.TestCase

class StringCurlyInspectionTestCase : TestCase() {
    fun testAll(): Unit = testInspection(StringCurlyInspection::class.java)
}
