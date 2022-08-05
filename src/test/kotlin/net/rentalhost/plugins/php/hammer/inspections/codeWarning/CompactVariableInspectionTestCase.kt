package net.rentalhost.plugins.php.hammer.inspections.codeWarning

import net.rentalhost.plugins.hammer.services.TestCase

class CompactVariableInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(CompactVariableInspection::class.java)

    fun testIncludeStrings(): Unit = testInspection(CompactVariableInspection::class.java, "includeStrings", {
        it.includeStrings = false
    })

    fun testIncludeArrays(): Unit = testInspection(CompactVariableInspection::class.java, "includeArrays", {
        it.includeArrays = false
    })
}
