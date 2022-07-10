package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.services.TestCase

class ComparisonScalarOrderInspectionTestCase: TestCase() {
    fun testOptionScalarRight(): Unit = testInspection(
        ComparisonScalarOrderInspection::class.java,
        "optionScalarSideRight",
        { it.optionScalarSide = OptionScalarSide.RIGHT }
    )

    fun testOptionScalarLeft(): Unit = testInspection(
        ComparisonScalarOrderInspection::class.java,
        "optionScalarSideLeft",
        { it.optionScalarSide = OptionScalarSide.LEFT }
    )
}
