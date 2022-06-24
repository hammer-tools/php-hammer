package net.rentalhost.plugins.php.hammer.inspections.codeStyle;

import net.rentalhost.plugins.services.TestCase;

public class NormalizeNativeTypesCaseInspectionTestCase
    extends TestCase {
    public void testAll() {
        testInspection(NormalizeNativeTypesCaseInspection.class);
    }
}
