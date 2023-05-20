package net.rentalhost.plugins.php.hammer.inspections.flowOptimization

import net.rentalhost.plugins.php.hammer.services.TestCase

class IfSimplificationAndInspectionTestCase : TestCase() {
  fun testAll(): Unit = testInspection(IfSimplificationAndInspection::class.java)
}
