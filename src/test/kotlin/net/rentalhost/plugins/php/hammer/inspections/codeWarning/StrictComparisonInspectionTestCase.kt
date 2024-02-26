package net.rentalhost.plugins.php.hammer.inspections.codeWarning

import net.rentalhost.plugins.php.hammer.services.TestCase

class StrictComparisonInspectionTestCase : TestCase() {
    fun testAll(): Unit = testInspection(StrictComparisonInspection::class.java)
}
