package net.rentalhost.plugins.php.hammer.inspections.flowOptimization

import net.rentalhost.plugins.php.hammer.services.TestCase

class TernarySimplificationInspectionTestCase : TestCase() {
  fun testAll(): Unit = testInspection(TernarySimplificationInspection::class.java)
}
