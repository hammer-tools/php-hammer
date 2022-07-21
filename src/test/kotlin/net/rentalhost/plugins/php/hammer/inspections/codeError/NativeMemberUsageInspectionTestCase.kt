package net.rentalhost.plugins.php.hammer.inspections.codeError

import net.rentalhost.plugins.services.TestCase

class NativeMemberUsageInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(NativeMemberUsageInspection::class.java)
}
