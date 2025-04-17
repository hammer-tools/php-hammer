package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.php.hammer.TestCase
import net.rentalhost.plugins.php.hammer.inspections.enums.OptionCaseSeparatorFormat

class CaseSeparatorFormatInspectionTestCase : TestCase() {
    fun testCaseSeparatorFormatColon(): Unit =
        testInspection(
            CaseSeparatorFormatInspection::class.java,
            "caseSeparatorFormatColon",
            { it.caseSeparatorFormat = OptionCaseSeparatorFormat.COLON }
        )

    fun testCaseSeparatorFormatSemicolon(): Unit =
        testInspection(
            CaseSeparatorFormatInspection::class.java,
            "caseSeparatorFormatSemicolon",
            { it.caseSeparatorFormat = OptionCaseSeparatorFormat.SEMICOLON }
        )
}
