package net.rentalhost.plugins.php.hammer.inspections.codeError

import net.rentalhost.plugins.php.hammer.TestCase

class CompactInsideShortFunctionInspectionTestCase : TestCase() {
    fun testAll(): Unit = testInspection(
        CompactInsideShortFunctionInspection::class.java,
        listOf("default", "dummy/functions.php")
    )
}
