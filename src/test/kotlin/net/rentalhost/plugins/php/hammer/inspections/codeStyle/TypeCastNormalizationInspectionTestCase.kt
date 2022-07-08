package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.services.TestCase

class TypeCastNormalizationInspectionTestCase: TestCase() {
    fun testOptionFormatShort(): Unit = testInspection(
        TypeCastNormalizationInspection::class.java,
        "optionFormatShort",
        { it.useOptionFormatShort(true) }
    )

    fun testOptionFormatLong(): Unit = testInspection(
        TypeCastNormalizationInspection::class.java,
        "optionFormatLong",
        { it.useOptionFormatShort(false) }
    )
}
