package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.enums.OptionTypeCastNormalizationFormat
import net.rentalhost.plugins.services.TestCase

class TypeCastNormalizationInspectionTestCase: TestCase() {
    fun testOptionFormatShort(): Unit = testInspection(
        TypeCastNormalizationInspection::class.java,
        "optionTypeCastNormalizationFormatShort",
        { it.optionTypeCastNormalizationFormat = OptionTypeCastNormalizationFormat.SHORT }
    )

    fun testOptionFormatLong(): Unit = testInspection(
        TypeCastNormalizationInspection::class.java,
        "optionTypeCastNormalizationFormatLong",
        { it.optionTypeCastNormalizationFormat = OptionTypeCastNormalizationFormat.LONG }
    )
}
