package net.rentalhost.plugins.php.hammer.inspections.deadCode

import net.rentalhost.plugins.php.hammer.TestCase

class UnusedUseVariableReferenceInspectionTestCase : TestCase() {
    fun testAll(): Unit = testInspection(UnusedUseVariableReferenceInspection::class.java)
}
