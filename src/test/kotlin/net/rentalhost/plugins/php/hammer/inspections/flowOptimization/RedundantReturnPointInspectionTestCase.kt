package net.rentalhost.plugins.php.hammer.inspections.flowOptimization

import net.rentalhost.plugins.php.hammer.services.TestCase

class RedundantReturnPointInspectionTestCase : TestCase() {
    fun testAll(): Unit = testInspection(RedundantReturnPointInspection::class.java)
}
