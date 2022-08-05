package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.hammer.services.TestCase

class StringCurlyInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(StringCurlyInspection::class.java)
}
