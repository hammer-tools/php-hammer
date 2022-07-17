package net.rentalhost.plugins.php.hammer.inspections.flowOptimization

import net.rentalhost.plugins.services.TestCase

class IfSimplificationOrInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(IfSimplificationOrInspection::class.java)
}
