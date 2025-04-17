package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.php.hammer.TestCase
import net.rentalhost.plugins.php.hammer.inspections.enums.OptionTypeCastNormalizationFormat

class TypeCastNormalizationInspectionTestCase : TestCase() {
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
