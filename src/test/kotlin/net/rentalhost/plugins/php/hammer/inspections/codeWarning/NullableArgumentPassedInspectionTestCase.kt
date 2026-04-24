package net.rentalhost.plugins.php.hammer.inspections.codeWarning

import net.rentalhost.plugins.php.hammer.TestCase

class NullableArgumentPassedInspectionTestCase : TestCase() {
    fun testAll(): Unit = testInspection(
        NullableArgumentPassedInspection::class.java,
        listOf("default")
    )
}
