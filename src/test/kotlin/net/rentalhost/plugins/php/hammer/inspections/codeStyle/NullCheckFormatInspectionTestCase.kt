package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.php.hammer.inspections.enums.OptionNullCheckFormat
import net.rentalhost.plugins.php.hammer.TestCase

class NullCheckFormatInspectionTestCase : TestCase() {
    fun testNullCheckFormatComparison(): Unit =
        testInspection(
            NullCheckFormatInspection::class.java,
            listOf("nullCheckFormatComparison", "dummy/functions.php"),
            { it.nullCheckFormat = OptionNullCheckFormat.COMPARISON }
        )

    fun testNullCheckFormatFunction(): Unit =
        testInspection(
            NullCheckFormatInspection::class.java,
            listOf("nullCheckFormatFunction", "dummy/functions.php"),
            { it.nullCheckFormat = OptionNullCheckFormat.FUNCTION }
        )
}
