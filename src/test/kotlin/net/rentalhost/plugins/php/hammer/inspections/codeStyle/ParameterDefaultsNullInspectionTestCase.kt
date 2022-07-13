package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.jetbrains.php.config.PhpLanguageLevel
import net.rentalhost.plugins.services.TestCase

class ParameterDefaultsNullInspectionTestCase: TestCase() {
    fun testPhp530(): Unit = testInspection(ParameterDefaultsNullInspection::class.java, phpLanguageLevel = PhpLanguageLevel.PHP530)

    fun testPhp700(): Unit = testInspection(ParameterDefaultsNullInspection::class.java, phpLanguageLevel = PhpLanguageLevel.PHP700)

    fun testElse(): Unit = testInspection(ParameterDefaultsNullInspection::class.java)

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
}
