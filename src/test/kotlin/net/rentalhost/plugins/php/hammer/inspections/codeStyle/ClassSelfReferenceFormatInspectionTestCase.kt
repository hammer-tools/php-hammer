package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.enums.OptionClassSelfReferenceFormat
import net.rentalhost.plugins.services.TestCase

class ClassSelfReferenceFormatInspectionTestCase: TestCase() {
    fun testOptionClassSelfReferenceFormatSelf(): Unit =
        testInspection(
            ClassSelfReferenceFormatInspection::class.java,
            "optionClassSelfReferenceFormatSelf",
            { it.optionClassSelfReferenceFormat = OptionClassSelfReferenceFormat.SELF }
        )

    fun testOptionClassSelfReferenceFormatNamed(): Unit =
        testInspection(
            ClassSelfReferenceFormatInspection::class.java,
            "optionClassSelfReferenceFormatNamed",
            { it.optionClassSelfReferenceFormat = OptionClassSelfReferenceFormat.NAMED }
        )
}
