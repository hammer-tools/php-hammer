package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.services.TestCase

class CaseSeparatorFormatInspectionTestCase: TestCase() {
    fun testOptionFormatColon(): Unit =
        testInspection(
            CaseSeparatorFormatInspection::class.java,
            "optionSeparatorFormatColon",
            { it.optionSeparatorFormat = OptionSeparatorFormat.COLON }
        )

    fun testOptionFormatSemicolon(): Unit =
        testInspection(
            CaseSeparatorFormatInspection::class.java,
            "optionSeparatorFormatSemicolon",
            { it.optionSeparatorFormat = OptionSeparatorFormat.SEMICOLON }
        )
}
