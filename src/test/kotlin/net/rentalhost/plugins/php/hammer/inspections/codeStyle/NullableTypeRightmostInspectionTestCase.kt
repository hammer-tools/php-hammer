package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.php.hammer.services.TestCase

class NullableTypeRightmostInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(NullableTypeRightmostInspection::class.java)
}
