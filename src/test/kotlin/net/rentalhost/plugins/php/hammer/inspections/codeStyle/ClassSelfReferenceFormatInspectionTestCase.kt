package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.services.TestCase

class ClassSelfReferenceFormatInspectionTestCase: TestCase() {
    fun testOptionReferenceFormatSelf(): Unit =
        testInspection(
            ClassSelfReferenceFormatInspection::class.java,
            "optionReferenceFormatSelf",
            { it.optionReferenceFormat = OptionReferenceFormat.SELF }
        )

    fun testOptionReferenceFormatNamed(): Unit =
        testInspection(
            ClassSelfReferenceFormatInspection::class.java,
            "optionReferenceFormatNamed",
            { it.optionReferenceFormat = OptionReferenceFormat.NAMED }
        )
}
