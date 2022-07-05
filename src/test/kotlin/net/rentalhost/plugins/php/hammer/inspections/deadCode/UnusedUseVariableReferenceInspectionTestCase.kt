package net.rentalhost.plugins.php.hammer.inspections.deadCode

import net.rentalhost.plugins.services.TestCase

class UnusedUseVariableReferenceInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(UnusedUseVariableReferenceInspection::class.java)
}
