package net.rentalhost.plugins.php.hammer.inspections.deprecation

import net.rentalhost.plugins.services.TestCase

class DollarSignOutsideCurlyBracesInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(DollarSignOutsideCurlyBracesInspection::class.java)
}
