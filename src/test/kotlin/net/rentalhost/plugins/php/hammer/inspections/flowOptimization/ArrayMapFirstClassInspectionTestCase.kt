package net.rentalhost.plugins.php.hammer.inspections.flowOptimization

import net.rentalhost.plugins.php.hammer.TestCase

class ArrayMapFirstClassInspectionTestCase : TestCase() {
    fun testAll(): Unit = testInspection(
        ArrayMapFirstClassInspection::class.java,
        listOf("default", "dummy/functions.php")
    )
}
