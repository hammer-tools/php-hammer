package net.rentalhost.plugins.php.hammer.inspections.flowOptimization

import net.rentalhost.plugins.php.hammer.TestCase

class RedundantReturnPointInspectionTestCase : TestCase() {
    fun testAll(): Unit = testInspection(RedundantReturnPointInspection::class.java)
}
