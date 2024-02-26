package net.rentalhost.plugins.php.hammer.inspections.codeSmell

import net.rentalhost.plugins.php.hammer.services.TestCase

class UselessIsComparisonInspectionTestCase : TestCase() {
    fun testAll(): Unit = testInspection(
        UselessIsComparisonInspection::class.java,
        listOf("all", "dummy/Native.php", "dummy/functions.php")
    )
}
