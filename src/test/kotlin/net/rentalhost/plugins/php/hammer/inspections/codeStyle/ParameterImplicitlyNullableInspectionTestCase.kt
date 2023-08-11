package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.php.hammer.inspections.enums.OptionNullableTypeFormat
import net.rentalhost.plugins.php.hammer.services.TestCase

class ParameterImplicitlyNullableInspectionTestCase : TestCase() {
  fun testAll(): Unit = testInspection(ParameterImplicitlyNullableInspection::class.java)

  fun testFormatShort(): Unit =
    testInspection(
      ParameterImplicitlyNullableInspection::class.java,
      "nullableTypeFormatShort",
      { it.nullableTypeFormat = OptionNullableTypeFormat.SHORT }
    )
}
