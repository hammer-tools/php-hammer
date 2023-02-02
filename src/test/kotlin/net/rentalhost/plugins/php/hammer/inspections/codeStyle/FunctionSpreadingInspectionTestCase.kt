package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.hammer.services.TestCase

class FunctionSpreadingInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(FunctionSpreadingInspection::class.java, listOf("default", "dummy/Native.php"))
}
