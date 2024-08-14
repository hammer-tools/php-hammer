package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.php.hammer.TestCase

class CastIntvalInspectionTestCase : TestCase() {
    fun testAll(): Unit = testInspection(CastIntvalInspection::class.java)
}
