package net.rentalhost.plugins.php.hammer.inspections.flowOptimization

import net.rentalhost.plugins.php.hammer.services.TestCase

class IfSimplificationOrInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(IfSimplificationOrInspection::class.java)
}
