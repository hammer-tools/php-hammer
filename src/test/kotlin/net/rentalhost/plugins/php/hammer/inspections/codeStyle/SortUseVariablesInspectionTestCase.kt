package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.hammer.services.TestCase

class SortUseVariablesInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(SortUseVariablesInspection::class.java)
}
