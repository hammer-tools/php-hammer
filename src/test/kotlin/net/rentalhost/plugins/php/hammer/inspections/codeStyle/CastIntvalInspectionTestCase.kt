package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.hammer.services.TestCase

class CastIntvalInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(CastIntvalInspection::class.java)
}
