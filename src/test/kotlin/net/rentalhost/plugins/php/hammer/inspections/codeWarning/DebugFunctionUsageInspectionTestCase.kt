package net.rentalhost.plugins.php.hammer.inspections.codeWarning

import net.rentalhost.plugins.php.hammer.services.TestCase

class DebugFunctionUsageInspectionTestCase : TestCase() {
  fun testAll(): Unit = testInspection(DebugFunctionUsageInspection::class.java)

  fun testNamespace(): Unit = testInspection(DebugFunctionUsageInspection::class.java, listOf("namespace", "dummy/Illuminate.php"))

  fun testProjectNamespace(): Unit = testInspection(DebugFunctionUsageInspection::class.java, listOf("projectNamespace", "dummy/functions.php"))
}
