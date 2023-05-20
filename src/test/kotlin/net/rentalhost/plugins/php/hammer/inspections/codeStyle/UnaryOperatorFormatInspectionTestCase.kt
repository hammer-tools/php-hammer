package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.php.hammer.inspections.enums.OptionUnaryOperatorSideFormat
import net.rentalhost.plugins.php.hammer.services.TestCase

class UnaryOperatorFormatInspectionTestCase : TestCase() {
  fun testUnaryOperatorSideLeft(): Unit = testInspection(
    UnaryOperatorFormatInspection::class.java,
    "unaryOperatorSideLeft",
    { it.unaryOperatorSide = OptionUnaryOperatorSideFormat.LEFT }
  )

  fun testUnaryOperatorSideRight(): Unit = testInspection(
    UnaryOperatorFormatInspection::class.java,
    "unaryOperatorSideRight",
    { it.unaryOperatorSide = OptionUnaryOperatorSideFormat.RIGHT }
  )

  fun testIncludeForRepeatedExpressionsDisabled(): Unit = testInspection(
    UnaryOperatorFormatInspection::class.java,
    "includeForRepeatedExpressionsDisabled",
    { it.includeForRepeatedExpressions = false }
  )
}
