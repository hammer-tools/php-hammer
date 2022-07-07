package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.services.TestCase

class ParameterImplicitlyNullableInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(ParameterImplicitlyNullableInspection::class.java)
}
