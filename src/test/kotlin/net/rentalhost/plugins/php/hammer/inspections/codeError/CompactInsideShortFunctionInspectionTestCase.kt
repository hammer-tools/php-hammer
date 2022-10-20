package net.rentalhost.plugins.php.hammer.inspections.codeError

import net.rentalhost.plugins.hammer.services.TestCase

class CompactInsideShortFunctionInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(CompactInsideShortFunctionInspection::class.java)
}
