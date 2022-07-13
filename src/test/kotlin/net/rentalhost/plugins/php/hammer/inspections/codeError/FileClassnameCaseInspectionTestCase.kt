package net.rentalhost.plugins.php.hammer.inspections.codeError

import net.rentalhost.plugins.services.TestCase

class FileClassnameCaseInspectionTestCase: TestCase() {
    fun testValid(): Unit = testInspection(FileClassnameCaseInspection::class.java, "Dummy")

    fun testIncorrectName(): Unit = testInspection(FileClassnameCaseInspection::class.java, "IncorrectName")

    fun testZeroExceptionA(): Unit = testInspection(FileClassnameCaseInspection::class.java, "0ExceptionA")

    fun testZeroExceptionB(): Unit = testInspection(FileClassnameCaseInspection::class.java, "0ExceptionB", {
        it.includeFilesWithInvalidIdentifier = true
    })

    fun testOptionIncludeNonRootedClassesDisabled(): Unit = testInspection(FileClassnameCaseInspection::class.java, "Rooted")

    fun testOptionIncludeNonRootedClasses(): Unit = testInspection(
        FileClassnameCaseInspection::class.java,
        "NonRooted",
        { it.includeNonRootedClasses = true }
    )

    fun testOptionIncludeFilesWithMultipleClasses(): Unit = testInspection(
        FileClassnameCaseInspection::class.java,
        "MultiClasses",
        { it.includeFilesWithMultipleClasses = true }
    )
}
