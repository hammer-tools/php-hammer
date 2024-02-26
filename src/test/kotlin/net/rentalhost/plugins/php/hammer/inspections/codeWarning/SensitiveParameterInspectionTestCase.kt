package net.rentalhost.plugins.php.hammer.inspections.codeWarning

import net.rentalhost.plugins.php.hammer.services.TestCase

class SensitiveParameterInspectionTestCase : TestCase() {
    fun testAll(): Unit = testInspection(SensitiveParameterInspection::class.java)

    fun testIgnoresTypesDisabled(): Unit = testInspection(
        SensitiveParameterInspection::class.java,
        "ignoreTypesDisabled", {
            it.ignoreTypes = false
        }
    )
}
