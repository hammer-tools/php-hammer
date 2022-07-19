package net.rentalhost.plugins.php.hammer.inspections.codeWarning

import net.rentalhost.plugins.services.TestCase

class DebugFunctionUsageInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(DebugFunctionUsageInspection::class.java)

    fun testNamespace(): Unit = testInspection(DebugFunctionUsageInspection::class.java, "namespace")
}
