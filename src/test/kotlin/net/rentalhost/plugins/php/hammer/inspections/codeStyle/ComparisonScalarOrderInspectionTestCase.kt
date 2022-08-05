package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.php.hammer.inspections.enums.OptionComparisonScalarSide
import net.rentalhost.plugins.hammer.services.TestCase

class ComparisonScalarOrderInspectionTestCase: TestCase() {
    fun testComparisonScalarRight(): Unit = testInspection(
        ComparisonScalarOrderInspection::class.java,
        "comparisonScalarSideRight",
        { it.comparisonScalarSide = OptionComparisonScalarSide.RIGHT }
    )

    fun testComparisonScalarLeft(): Unit = testInspection(
        ComparisonScalarOrderInspection::class.java,
        "comparisonScalarSideLeft",
        { it.comparisonScalarSide = OptionComparisonScalarSide.LEFT }
    )
}
