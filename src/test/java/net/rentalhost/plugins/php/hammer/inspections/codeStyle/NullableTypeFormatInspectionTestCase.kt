package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.services.TestCase

class NullableTypeFormatInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(NullableTypeFormatInspection::class.java)
}
