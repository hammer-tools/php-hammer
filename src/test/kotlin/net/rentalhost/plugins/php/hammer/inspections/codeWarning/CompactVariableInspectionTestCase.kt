package net.rentalhost.plugins.php.hammer.inspections.codeWarning

import net.rentalhost.plugins.services.TestCase

class CompactVariableInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(CompactVariableInspection::class.java)
}
