package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.enums.OptionUnaryOperatorSideFormat
import net.rentalhost.plugins.services.TestCase

class UnaryOperatorFormatInspectionTestCase: TestCase() {
    fun testOptionUnaryOperatorSideLeft(): Unit = testInspection(
        UnaryOperatorFormatInspection::class.java,
        "unaryOperatorSideLeft",
        { it.unaryOperatorSide = OptionUnaryOperatorSideFormat.LEFT }
    )

    fun testOptionUnaryOperatorSideRight(): Unit = testInspection(
        UnaryOperatorFormatInspection::class.java,
        "unaryOperatorSideRight",
        { it.unaryOperatorSide = OptionUnaryOperatorSideFormat.RIGHT }
    )

    fun testOptionIncludeForRepeatedExpressionsDisabled(): Unit = testInspection(
        UnaryOperatorFormatInspection::class.java,
        "includeForRepeatedExpressionsDisabled",
        { it.includeForRepeatedExpressions = false }
    )
}
