package net.rentalhost.plugins.php.hammer.inspections.codeWarning

import net.rentalhost.plugins.php.hammer.services.TestCase

class CompactVariableInspectionTestCase : TestCase() {
    fun testAll(): Unit = testInspection(
        CompactVariableInspection::class.java,
        listOf("default", "dummy/functions.php")
    )

    fun testIncludeStrings(): Unit = testInspection(CompactVariableInspection::class.java,
        listOf("includeStrings", "dummy/functions.php"), {
            it.includeStrings = false
        })

    fun testIncludeArrays(): Unit = testInspection(CompactVariableInspection::class.java,
        listOf("includeArrays", "dummy/functions.php"), {
            it.includeArrays = false
        })
}
