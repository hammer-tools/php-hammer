package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.php.hammer.TestCase

class AnonymousFunctionStaticInspectionTestCase : TestCase() {
    fun testAll(): Unit = testInspection(AnonymousFunctionStaticInspection::class.java)

    fun testIncludeShortFunctionsDisabled(): Unit = testInspection(
        AnonymousFunctionStaticInspection::class.java,
        "includeShortFunctionsDisabled",
        { it.includeShortFunctions = false }
    )
}
