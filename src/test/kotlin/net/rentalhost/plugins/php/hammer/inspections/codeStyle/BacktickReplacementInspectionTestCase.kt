package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.php.hammer.TestCase

class BacktickReplacementInspectionTestCase : TestCase() {
    fun testAll(): Unit = testInspection(BacktickReplacementInspection::class.java)
}
