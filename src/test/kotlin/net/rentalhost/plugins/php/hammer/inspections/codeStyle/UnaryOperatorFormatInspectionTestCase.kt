package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.enums.OptionUnaryOperatorSideFormat
import net.rentalhost.plugins.services.TestCase

class UnaryOperatorFormatInspectionTestCase: TestCase() {
    fun testOptionUnaryOperatorSideLeft(): Unit = testInspection(
        UnaryOperatorFormatInspection::class.java,
        "optionUnaryOperatorSideLeft",
        { it.optionUnaryOperatorSide = OptionUnaryOperatorSideFormat.LEFT }
    )

    fun testOptionUnaryOperatorSideRight(): Unit = testInspection(
        UnaryOperatorFormatInspection::class.java,
        "optionUnaryOperatorSideRight",
        { it.optionUnaryOperatorSide = OptionUnaryOperatorSideFormat.RIGHT }
    )

    fun testOptionIncludeForRepeatedExpressionsDisabled(): Unit = testInspectionOnly(
        UnaryOperatorFormatInspection::class.java,
        "includeForRepeatedExpressionsDisabled",
        { it.includeForRepeatedExpressions = false }
    )
}
