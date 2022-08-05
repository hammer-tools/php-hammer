package net.rentalhost.plugins.php.hammer.inspections.flowOptimization

import net.rentalhost.plugins.hammer.services.TestCase

class ArrayMapFirstClassInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(ArrayMapFirstClassInspection::class.java)
}
