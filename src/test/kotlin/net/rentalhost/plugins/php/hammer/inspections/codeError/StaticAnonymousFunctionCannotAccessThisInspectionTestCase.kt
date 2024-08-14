package net.rentalhost.plugins.php.hammer.inspections.codeError

import net.rentalhost.plugins.php.hammer.TestCase

class StaticAnonymousFunctionCannotAccessThisInspectionTestCase : TestCase() {
    fun testAll(): Unit = testInspection(StaticAnonymousFunctionCannotAccessThisInspection::class.java)
}
