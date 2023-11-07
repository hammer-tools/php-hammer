package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.jetbrains.php.config.PhpLanguageLevel
import net.rentalhost.plugins.php.hammer.services.TestCase

class ParameterDefaultsNullInspectionTestCase : TestCase() {
  fun testAllPhp530(): Unit = testInspection(ParameterDefaultsNullInspection::class.java, phpLanguageLevel = PhpLanguageLevel.PHP530)

  fun testAllPhp700(): Unit = testInspection(ParameterDefaultsNullInspection::class.java, phpLanguageLevel = PhpLanguageLevel.PHP700)

  fun testAll(): Unit = testInspection(ParameterDefaultsNullInspection::class.java)

  fun testIncludeAbstractMethods(): Unit = testInspection(
    ParameterDefaultsNullInspection::class.java,
    "includeAbstractMethods",
    { it.includeAbstractMethods = true }
  )

  fun testIncludeOverriddenMethods(): Unit = testInspection(
    ParameterDefaultsNullInspection::class.java,
    "includeOverriddenMethods",
    { it.includeOverriddenMethods = true }
  )

  fun testIncludeNullableParameters(): Unit = testInspection(
    ParameterDefaultsNullInspection::class.java,
    "includeNullableParameters",
    { it.includeNullableParameters = true }
  )

  fun testIncludeParametersWithReference(): Unit = testInspection(
    ParameterDefaultsNullInspection::class.java,
    "includeParametersWithReference",
    { it.includeParametersWithReference = true }
  )

  fun testIncludeBooleansDisabled(): Unit = testInspection(
    ParameterDefaultsNullInspection::class.java,
    "includeBooleansDisabled",
    { it.includeBooleans = false }
  )

  fun testIncludeLastParameter(): Unit = testInspection(
    ParameterDefaultsNullInspection::class.java,
    "includeLastParameter",
    { it.includeLatestParameter = true }
  )

  fun testIncludeReadonly(): Unit = testInspection(
    ParameterDefaultsNullInspection::class.java,
    "includeReadonly",
    { it.includeReadonly = true }
  )
}
