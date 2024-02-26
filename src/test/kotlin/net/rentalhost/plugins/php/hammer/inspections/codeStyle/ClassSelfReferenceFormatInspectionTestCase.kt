package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.php.hammer.inspections.enums.OptionClassSelfReferenceFormat
import net.rentalhost.plugins.php.hammer.services.TestCase

class ClassSelfReferenceFormatInspectionTestCase : TestCase() {
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
