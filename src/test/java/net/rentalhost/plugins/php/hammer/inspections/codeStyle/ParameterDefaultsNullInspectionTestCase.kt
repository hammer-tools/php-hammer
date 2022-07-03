package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.services.TestCase

class ParameterDefaultsNullInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(ParameterDefaultsNullInspection::class.java)
}
