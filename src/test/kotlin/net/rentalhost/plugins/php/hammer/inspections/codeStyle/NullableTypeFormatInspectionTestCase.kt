package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.services.TestCase

class NullableTypeFormatInspectionTestCase: TestCase() {
    fun testOptionFormatLong(): Unit =
        testInspection(
            NullableTypeFormatInspection::class.java,
            "optionFormatLong",
            { it.useFormatLong(true) }
        )

    fun testOptionFormatShort(): Unit =
        testInspection(
            NullableTypeFormatInspection::class.java,
            "optionFormatShort",
            { it.useFormatLong(false) }
        )
}
