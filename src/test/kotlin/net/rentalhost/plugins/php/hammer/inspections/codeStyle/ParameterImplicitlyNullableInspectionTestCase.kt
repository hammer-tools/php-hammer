package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.hammer.services.TestCase

class ParameterImplicitlyNullableInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(ParameterImplicitlyNullableInspection::class.java)
}
