package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.services.TestCase

class NullableTypesFormatInspectionTestCase : TestCase() {
    fun testAll() = testInspection(NullableTypesFormatInspection::class.java)
}
