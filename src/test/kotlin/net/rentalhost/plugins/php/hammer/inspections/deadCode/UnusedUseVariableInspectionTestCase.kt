package net.rentalhost.plugins.php.hammer.inspections.deadCode

import net.rentalhost.plugins.hammer.services.TestCase

class UnusedUseVariableInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(UnusedUseVariableInspection::class.java)
}
