package net.rentalhost.plugins.php.hammer.inspections.codeSmell

import net.rentalhost.plugins.hammer.services.TestCase

class FunctionErrorSilencedInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(FunctionErrorSilencedInspection::class.java)
}
