package net.rentalhost.plugins.php.hammer.inspections.codeError

import net.rentalhost.plugins.php.hammer.services.TestCase

class FileClassnameCaseInspectionTestCase : TestCase() {
  fun testValid(): Unit = testInspection(FileClassnameCaseInspection::class.java, "Dummy")

  fun testNamespaced(): Unit = testInspection(FileClassnameCaseInspection::class.java, "NamespacedDummy")

  fun testIncorrectName(): Unit = testInspection(FileClassnameCaseInspection::class.java, "IncorrectName")

  fun testZeroExceptionA(): Unit = testInspection(FileClassnameCaseInspection::class.java, "0ExceptionA")

  fun testZeroExceptionB(): Unit = testInspection(FileClassnameCaseInspection::class.java, "0ExceptionB", {
    it.includeFilesWithInvalidIdentifier = true
  })

  fun testIncludeNonRootedClassesDisabled(): Unit = testInspection(FileClassnameCaseInspection::class.java, "Rooted")

  fun testIncludeNonRootedClasses(): Unit = testInspection(
    FileClassnameCaseInspection::class.java,
    "NonRooted",
    { it.includeNonRootedClasses = true }
  )

  fun testIncludeFilesWithMultipleClasses(): Unit = testInspection(
    FileClassnameCaseInspection::class.java,
    "MultiClasses",
    { it.includeFilesWithMultipleClasses = true }
  )
}
