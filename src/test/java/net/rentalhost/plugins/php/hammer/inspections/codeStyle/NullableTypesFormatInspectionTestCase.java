package net.rentalhost.plugins.php.hammer.inspections.codeStyle;

import net.rentalhost.plugins.services.TestCase;

public class NullableTypesFormatInspectionTestCase
    extends TestCase {
    public void testAll() {
        testInspection(NullableTypesFormatInspection.class);
    }
}
