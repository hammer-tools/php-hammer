package net.rentalhost.plugins.php.hammer.inspections.flowOptimization

import net.rentalhost.plugins.services.TestCase

class TernarySimplificationInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(TernarySimplificationInspection::class.java)
}
