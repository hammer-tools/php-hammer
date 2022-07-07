package net.rentalhost.plugins.php.hammer.inspections.codeError

import net.rentalhost.plugins.services.TestCase

class StaticAnonymousFunctionCannotAccessThisInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(StaticAnonymousFunctionCannotAccessThisInspection::class.java)
}
