package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.services.TestCase

class ToStringSimplificationInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(ToStringSimplificationInspection::class.java)
}
