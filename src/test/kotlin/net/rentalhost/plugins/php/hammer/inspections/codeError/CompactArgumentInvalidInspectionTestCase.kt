package net.rentalhost.plugins.php.hammer.inspections.codeError

import net.rentalhost.plugins.hammer.services.TestCase

class CompactArgumentInvalidInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(CompactArgumentInvalidInspection::class.java)
}
