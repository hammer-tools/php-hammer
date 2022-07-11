package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.enums.OptionCaseSeparatorFormat
import net.rentalhost.plugins.services.TestCase

class CaseSeparatorFormatInspectionTestCase: TestCase() {
    fun testOptionCaseSeparatorFormatColon(): Unit =
        testInspection(
            CaseSeparatorFormatInspection::class.java,
            "optionCaseSeparatorFormatColon",
            { it.optionCaseSeparatorFormat = OptionCaseSeparatorFormat.COLON }
        )

    fun testOptionCaseSeparatorFormatSemicolon(): Unit =
        testInspection(
            CaseSeparatorFormatInspection::class.java,
            "optionCaseSeparatorFormatSemicolon",
            { it.optionCaseSeparatorFormat = OptionCaseSeparatorFormat.SEMICOLON }
        )
}
