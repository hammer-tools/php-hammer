package net.rentalhost.plugins.php.hammer.inspections.codeSmell

import net.rentalhost.plugins.hammer.services.TestCase

class FrameworkOptionalReplacementInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(FrameworkOptionalReplacementInspection::class.java)
}
