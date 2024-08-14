package net.rentalhost.plugins.php.hammer.inspections.flowOptimization

import net.rentalhost.plugins.php.hammer.TestCase

class ReturnTernaryReplacementInspectionTestCase : TestCase() {
    fun testAll(): Unit = testInspection(ReturnTernaryReplacementInspection::class.java)
}
