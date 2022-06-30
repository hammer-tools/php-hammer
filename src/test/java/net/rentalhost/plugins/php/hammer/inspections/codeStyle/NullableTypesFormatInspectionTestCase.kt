package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.services.TestCase

class NullableTypesFormatInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(NullableTypesFormatInspection::class.java)
}
