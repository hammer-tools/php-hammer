package net.rentalhost.plugins.php.hammer.inspections.codeStyle;

import net.rentalhost.plugins.services.TestCase;

public class NullableTypeRightmostInspectionTestCase
    extends TestCase {
    public void testAll() {
        testInspection(NullableTypeRightmostInspection.class);
    }
}
