package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.hammer.services.TestCase

class ClassnameLiteralInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(ClassnameLiteralInspection::class.java, listOf("default", "dummy/Root.php", "dummy/Illuminate.php"))
}
