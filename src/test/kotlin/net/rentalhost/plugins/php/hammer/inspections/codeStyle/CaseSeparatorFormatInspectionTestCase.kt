package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.services.TestCase

class CaseSeparatorFormatInspectionTestCase: TestCase() {
    fun testOptionFormatColon(): Unit =
        testInspection(
            CaseSeparatorFormatInspection::class.java,
            "optionFormatColon",
            { it.useFormatColon(true) }
        )

    fun testOptionFormatSemicolon(): Unit =
        testInspection(
            CaseSeparatorFormatInspection::class.java,
            "optionFormatSemicolon",
            { it.useFormatColon(false) }
        )
}
