package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.enums.OptionTypeCastNormalizationFormat
import net.rentalhost.plugins.services.TestCase

class TypeCastNormalizationInspectionTestCase: TestCase() {
    fun testFormatShort(): Unit = testInspection(
        TypeCastNormalizationInspection::class.java,
        "typeCastNormalizationFormatShort",
        { it.typeCastNormalizationFormat = OptionTypeCastNormalizationFormat.SHORT }
    )

    fun testFormatLong(): Unit = testInspection(
        TypeCastNormalizationInspection::class.java,
        "typeCastNormalizationFormatLong",
        { it.typeCastNormalizationFormat = OptionTypeCastNormalizationFormat.LONG }
    )
}
