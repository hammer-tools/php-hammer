package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.enums.OptionClassSelfReferenceFormat
import net.rentalhost.plugins.services.TestCase

class ClassSelfReferenceFormatInspectionTestCase: TestCase() {
    fun testClassSelfReferenceFormatSelf(): Unit =
        testInspection(
            ClassSelfReferenceFormatInspection::class.java,
            "classSelfReferenceFormatSelf",
            { it.classSelfReferenceFormat = OptionClassSelfReferenceFormat.SELF }
        )

    fun testClassSelfReferenceFormatNamed(): Unit =
        testInspection(
            ClassSelfReferenceFormatInspection::class.java,
            "classSelfReferenceFormatNamed",
            { it.classSelfReferenceFormat = OptionClassSelfReferenceFormat.NAMED }
        )
}
