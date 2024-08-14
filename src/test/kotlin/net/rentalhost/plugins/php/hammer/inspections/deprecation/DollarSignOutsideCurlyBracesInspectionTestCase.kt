package net.rentalhost.plugins.php.hammer.inspections.deprecation

import net.rentalhost.plugins.php.hammer.TestCase

class DollarSignOutsideCurlyBracesInspectionTestCase : TestCase() {
    fun testAll(): Unit = testInspection(DollarSignOutsideCurlyBracesInspection::class.java)
}
