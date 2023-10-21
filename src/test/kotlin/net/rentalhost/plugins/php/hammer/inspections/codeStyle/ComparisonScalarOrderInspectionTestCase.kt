package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.php.hammer.inspections.enums.OptionComparisonScalarSide
import net.rentalhost.plugins.php.hammer.services.TestCase

class ComparisonScalarOrderInspectionTestCase : TestCase() {
  fun testComparisonScalarRight(): Unit = testInspection(
    ComparisonScalarOrderInspection::class.java,
    "comparisonScalarSideRight",
    { it.comparisonScalarSide = OptionComparisonScalarSide.RIGHT }
  )

  fun testSwapRightAssignments(): Unit = testInspection(
    ComparisonScalarOrderInspection::class.java,
    "swapRightAssignments",
    {
      it.comparisonScalarSide = OptionComparisonScalarSide.RIGHT
      it.swapRightAssignments = true
    }
  )

  fun testComparisonScalarLeft(): Unit = testInspection(
    ComparisonScalarOrderInspection::class.java,
    "comparisonScalarSideLeft",
    { it.comparisonScalarSide = OptionComparisonScalarSide.LEFT }
  )
}
