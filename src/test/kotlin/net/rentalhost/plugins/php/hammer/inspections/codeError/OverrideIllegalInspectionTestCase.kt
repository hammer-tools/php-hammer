package net.rentalhost.plugins.php.hammer.inspections.codeError

import net.rentalhost.plugins.php.hammer.services.TestCase

class OverrideIllegalInspectionTestCase : TestCase() {
    fun testAll(): Unit = testInspection(
        OverrideIllegalInspection::class.java,
        listOf("default", "dummy/Namespaced.php")
    )

    fun testConsiderUnusedTraits(): Unit = testInspection(
        OverrideIllegalInspection::class.java,
        listOf("considerUnusedTraits", "dummy/Namespaced.php"), {
            it.considerUnusedTraits = true
        }
    )
}
