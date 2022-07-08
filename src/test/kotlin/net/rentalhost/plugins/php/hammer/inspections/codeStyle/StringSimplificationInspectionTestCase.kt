package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.services.TestCase

class StringSimplificationInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(StringSimplificationInspection::class.java)
}
