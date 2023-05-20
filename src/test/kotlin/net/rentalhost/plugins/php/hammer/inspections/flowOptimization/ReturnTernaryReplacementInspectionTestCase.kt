package net.rentalhost.plugins.php.hammer.inspections.flowOptimization

import net.rentalhost.plugins.php.hammer.services.TestCase

class ReturnTernaryReplacementInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(ReturnTernaryReplacementInspection::class.java)
}
