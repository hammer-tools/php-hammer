package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.services.TestCase

class CastIntvalInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(CastIntvalInspection::class.java)
}
