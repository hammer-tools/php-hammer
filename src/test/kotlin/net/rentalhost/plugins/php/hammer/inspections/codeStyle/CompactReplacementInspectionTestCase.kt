package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.php.hammer.services.TestCase

class CompactReplacementInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(CompactReplacementInspection::class.java)
}
