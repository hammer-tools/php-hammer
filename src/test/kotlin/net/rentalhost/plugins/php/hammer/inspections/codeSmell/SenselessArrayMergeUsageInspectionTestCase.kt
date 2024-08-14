package net.rentalhost.plugins.php.hammer.inspections.codeSmell

import net.rentalhost.plugins.php.hammer.TestCase

class SenselessArrayMergeUsageInspectionTestCase : TestCase() {
    fun testAll(): Unit = testInspection(
        SenselessArrayMergeUsageInspection::class.java,
        listOf("default", "dummy/functions.php")
    )
}
