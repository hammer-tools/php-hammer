package net.rentalhost.plugins.php.hammer.inspections.codeError

import net.rentalhost.plugins.php.hammer.TestCase

class CompactArgumentInvalidInspectionTestCase : TestCase() {
    fun testAll(): Unit = testInspection(
        CompactArgumentInvalidInspection::class.java,
        listOf("default", "dummy/functions.php")
    )
}
