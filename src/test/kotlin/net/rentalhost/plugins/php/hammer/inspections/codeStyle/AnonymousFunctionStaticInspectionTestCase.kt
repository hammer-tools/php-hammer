package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.services.TestCase

class AnonymousFunctionStaticInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(AnonymousFunctionStaticInspection::class.java)
}
