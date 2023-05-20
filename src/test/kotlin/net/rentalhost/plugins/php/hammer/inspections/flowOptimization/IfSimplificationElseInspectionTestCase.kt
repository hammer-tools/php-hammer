package net.rentalhost.plugins.php.hammer.inspections.flowOptimization

import net.rentalhost.plugins.php.hammer.services.TestCase

class IfSimplificationElseInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(IfSimplificationElseInspection::class.java)
}
