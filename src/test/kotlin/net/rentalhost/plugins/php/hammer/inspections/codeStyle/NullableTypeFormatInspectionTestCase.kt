package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.php.hammer.inspections.enums.OptionNullableTypeFormat
import net.rentalhost.plugins.php.hammer.services.TestCase

class NullableTypeFormatInspectionTestCase : TestCase() {
    fun testFormatLong(): Unit =
        testInspection(
            NullableTypeFormatInspection::class.java,
            "nullableTypeFormatLong",
            { it.nullableTypeFormat = OptionNullableTypeFormat.LONG }
        )

    fun testFormatShort(): Unit =
        testInspection(
            NullableTypeFormatInspection::class.java,
            "nullableTypeFormatShort",
            { it.nullableTypeFormat = OptionNullableTypeFormat.SHORT }
        )
}
