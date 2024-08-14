package net.rentalhost.plugins.php.hammer.inspections.codeSmell

import net.rentalhost.plugins.php.hammer.TestCase

class CompactDuplicatedTermsInspectionTestCase : TestCase() {
    fun testAll(): Unit = testInspection(
        CompactDuplicatedTermsInspection::class.java,
        listOf("default", "dummy/functions.php")
    )
}
