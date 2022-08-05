package net.rentalhost.plugins.php.hammer.inspections.codeWarning

import net.rentalhost.plugins.hammer.services.TestCase

class BackslashFilenameUsageInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(BackslashFilenameUsageInspection::class.java)
}
