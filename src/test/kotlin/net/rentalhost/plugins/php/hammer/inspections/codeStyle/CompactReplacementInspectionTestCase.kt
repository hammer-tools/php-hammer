package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import net.rentalhost.plugins.php.hammer.inspections.enums.OptionCompactArgumentsFormat
import net.rentalhost.plugins.php.hammer.TestCase

class CompactReplacementInspectionTestCase : TestCase() {
    fun testAll(): Unit = testInspection(CompactReplacementInspection::class.java)

    fun testCompactReplacementFormatNamed(): Unit =
        testInspection(
            CompactReplacementInspection::class.java,
            "compactArgumentsFormatNamed",
            { it.compactArgumentsFormat = OptionCompactArgumentsFormat.NAMED }
        )
}
