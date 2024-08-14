package net.rentalhost.plugins.php.hammer.inspections.codeWarning

import net.rentalhost.plugins.php.hammer.TestCase

class ObsoleteFunctionInspectionTestCase : TestCase() {
    fun testAll(): Unit = testInspection(ObsoleteFunctionInspection::class.java)
}
