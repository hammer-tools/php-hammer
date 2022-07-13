package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.enums.OptionNullableTypeFormat
import net.rentalhost.plugins.services.TestCase

class NullableTypeFormatInspectionTestCase: TestCase() {
    fun testOptionFormatLong(): Unit =
        testInspection(
            NullableTypeFormatInspection::class.java,
            "nullableTypeFormatLong",
            { it.nullableTypeFormat = OptionNullableTypeFormat.LONG }
        )

    fun testOptionFormatShort(): Unit =
        testInspection(
            NullableTypeFormatInspection::class.java,
            "nullableTypeFormatShort",
            { it.nullableTypeFormat = OptionNullableTypeFormat.SHORT }
        )
}
