package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.services.TestCase

class ComparisonScalarOrderInspectionTestCase: TestCase() {
    fun testOptionScalarRight(): Unit = testInspection(
        ComparisonScalarOrderInspection::class.java,
        "optionScalarRight",
        { it.useScalarRight(true) }
    )

    fun testOptionScalarLeft(): Unit = testInspection(
        ComparisonScalarOrderInspection::class.java,
        "optionScalarLeft",
        { it.useScalarRight(false) }
    )
}
