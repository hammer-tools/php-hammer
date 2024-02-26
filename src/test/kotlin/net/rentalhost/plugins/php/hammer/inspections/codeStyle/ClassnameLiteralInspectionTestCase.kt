package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.php.hammer.services.TestCase

class ClassnameLiteralInspectionTestCase : TestCase() {
    fun testAll(): Unit = testInspection(
        ClassnameLiteralInspection::class.java,
        listOf("default", "dummy/functions.php", "dummy/Root.php", "dummy/Illuminate.php")
    )

    fun testIncludeClassCheckersDisabled(): Unit = testInspection(
        ClassnameLiteralInspection::class.java,
        listOf("includeClassCheckersDisabled", "dummy/functions.php", "dummy/Root.php", "dummy/Illuminate.php"),
        { it.includeClassCheckers = false }
    )

    fun testIncludeNonexistentClassesDisabled(): Unit = testInspection(
        ClassnameLiteralInspection::class.java,
        listOf("includeNonexistentClasses", "dummy/functions.php"),
        { it.includeNonexistentClasses = true }
    )
}
