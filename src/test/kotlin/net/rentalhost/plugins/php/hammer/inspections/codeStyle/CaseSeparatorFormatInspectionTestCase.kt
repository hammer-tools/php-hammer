package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.enums.OptionCaseSeparatorFormat
import net.rentalhost.plugins.services.TestCase

class CaseSeparatorFormatInspectionTestCase: TestCase() {
    fun testOptionCaseSeparatorFormatColon(): Unit =
        testInspection(
            CaseSeparatorFormatInspection::class.java,
            "caseSeparatorFormatColon",
            { it.caseSeparatorFormat = OptionCaseSeparatorFormat.COLON }
        )

    fun testOptionCaseSeparatorFormatSemicolon(): Unit =
        testInspection(
            CaseSeparatorFormatInspection::class.java,
            "caseSeparatorFormatSemicolon",
            { it.caseSeparatorFormat = OptionCaseSeparatorFormat.SEMICOLON }
        )
}
