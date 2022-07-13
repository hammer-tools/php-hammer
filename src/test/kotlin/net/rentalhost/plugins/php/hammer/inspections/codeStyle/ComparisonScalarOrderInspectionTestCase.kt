package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.enums.OptionComparisonScalarSide
import net.rentalhost.plugins.services.TestCase

class ComparisonScalarOrderInspectionTestCase: TestCase() {
    fun testOptionComparisonScalarRight(): Unit = testInspection(
        ComparisonScalarOrderInspection::class.java,
        "comparisonScalarSideRight",
        { it.comparisonScalarSide = OptionComparisonScalarSide.RIGHT }
    )

    fun testOptionComparisonScalarLeft(): Unit = testInspection(
        ComparisonScalarOrderInspection::class.java,
        "comparisonScalarSideLeft",
        { it.comparisonScalarSide = OptionComparisonScalarSide.LEFT }
    )
}
