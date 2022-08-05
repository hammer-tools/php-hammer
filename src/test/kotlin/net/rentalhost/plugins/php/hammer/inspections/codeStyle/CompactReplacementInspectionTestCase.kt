package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.hammer.services.TestCase

class CompactReplacementInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(CompactReplacementInspection::class.java)
}
