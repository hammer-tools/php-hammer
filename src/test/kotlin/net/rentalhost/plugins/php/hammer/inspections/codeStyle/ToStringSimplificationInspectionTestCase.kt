package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.php.hammer.services.TestCase

class ToStringSimplificationInspectionTestCase : TestCase() {
    fun testAll(): Unit = testInspection(ToStringSimplificationInspection::class.java)
}
