package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.enums.OptionNullableTypeFormat
import net.rentalhost.plugins.services.TestCase

class NullableTypeFormatInspectionTestCase: TestCase() {
    fun testOptionFormatLong(): Unit =
        testInspection(
            NullableTypeFormatInspection::class.java,
            "optionNullableTypeFormatLong",
            { it.optionNullableTypeFormat = OptionNullableTypeFormat.LONG }
        )

    fun testOptionFormatShort(): Unit =
        testInspection(
            NullableTypeFormatInspection::class.java,
            "optionNullableTypeFormatShort",
            { it.optionNullableTypeFormat = OptionNullableTypeFormat.SHORT }
        )
}
