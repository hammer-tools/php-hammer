package net.rentalhost.plugins.php.hammer.inspections.codeError

import net.rentalhost.plugins.services.TestCase

class FileClassnameCaseInspectionTestCase: TestCase() {
    fun testValid(): Unit = testInspection(FileClassnameCaseInspection::class.java, "Dummy")

    fun testIncorrectName(): Unit = testInspection(FileClassnameCaseInspection::class.java, "IncorrectName")
}
