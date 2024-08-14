package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.php.hammer.TestCase

class ArrayPackableInspectionTestCase : TestCase() {
    fun testAll(): Unit = testInspection(ArrayPackableInspection::class.java)
}
