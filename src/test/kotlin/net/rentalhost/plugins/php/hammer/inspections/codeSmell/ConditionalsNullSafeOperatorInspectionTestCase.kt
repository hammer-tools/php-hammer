package net.rentalhost.plugins.php.hammer.inspections.codeSmell

import net.rentalhost.plugins.php.hammer.services.TestCase

class ConditionalsNullSafeOperatorInspectionTestCase : TestCase() {
  fun testAll(): Unit = testInspection(
    ConditionalsNullSafeOperatorInspection::class.java,
    listOf("default", "dummy/Root.php")
  )
}
