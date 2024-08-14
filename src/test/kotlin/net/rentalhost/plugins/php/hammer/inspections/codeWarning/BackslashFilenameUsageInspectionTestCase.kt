package net.rentalhost.plugins.php.hammer.inspections.codeWarning

import net.rentalhost.plugins.php.hammer.TestCase

class BackslashFilenameUsageInspectionTestCase : TestCase() {
    fun testAll(): Unit = testInspection(BackslashFilenameUsageInspection::class.java)
}
