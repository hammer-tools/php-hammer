package net.rentalhost.plugins.php.hammer.inspections.codeSmell

import net.rentalhost.plugins.services.TestCase

class CompactDuplicatedTermsInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(CompactDuplicatedTermsInspection::class.java)
}
