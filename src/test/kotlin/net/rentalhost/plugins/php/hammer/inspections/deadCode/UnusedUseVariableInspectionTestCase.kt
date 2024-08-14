package net.rentalhost.plugins.php.hammer.inspections.deadCode

import net.rentalhost.plugins.php.hammer.TestCase

class UnusedUseVariableInspectionTestCase : TestCase() {
    fun testAll(): Unit = testInspection(UnusedUseVariableInspection::class.java)
}
