package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.enums.OptionComparisonScalarSide
import net.rentalhost.plugins.services.TestCase

class ComparisonScalarOrderInspectionTestCase: TestCase() {
    fun testOptionComparisonScalarRight(): Unit = testInspection(
        ComparisonScalarOrderInspection::class.java,
        "optionComparisonScalarSideRight",
        { it.optionComparisonScalarSide = OptionComparisonScalarSide.RIGHT }
    )

    fun testOptionComparisonScalarLeft(): Unit = testInspection(
        ComparisonScalarOrderInspection::class.java,
        "optionComparisonScalarSideLeft",
        { it.optionComparisonScalarSide = OptionComparisonScalarSide.LEFT }
    )
}
