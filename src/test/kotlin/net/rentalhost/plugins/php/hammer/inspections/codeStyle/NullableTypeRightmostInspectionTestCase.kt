package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.services.TestCase

class NullableTypeRightmostInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(NullableTypeRightmostInspection::class.java)
}
