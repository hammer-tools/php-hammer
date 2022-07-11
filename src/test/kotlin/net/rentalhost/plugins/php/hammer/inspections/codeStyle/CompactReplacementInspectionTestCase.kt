package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.services.TestCase

class CompactReplacementInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(CompactReplacementInspection::class.java)
}
