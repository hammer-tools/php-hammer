package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.jetbrains.php.config.PhpLanguageLevel
import net.rentalhost.plugins.php.hammer.TestCase
import net.rentalhost.plugins.php.hammer.inspections.enums.OptionNullableTypeFormat

class ParameterImplicitlyNullableInspectionTestCase : TestCase() {
    fun testAll(): Unit = testInspection(ParameterImplicitlyNullableInspection::class.java)

    fun testFormatShort(): Unit =
        testInspection(
            ParameterImplicitlyNullableInspection::class.java,
            "nullableTypeFormatShort",
            { it.nullableTypeFormat = OptionNullableTypeFormat.SHORT }
        )

    // It will force use short format automatically.
    fun testFormatLongBefore800(): Unit =
        testInspection(
            ParameterImplicitlyNullableInspection::class.java,
            "nullableTypeFormatLongBefore800",
            { },
            PhpLanguageLevel.PHP710
        )
}
