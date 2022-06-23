package net.rentalhost.plugins.services;

import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.jetbrains.php.lang.inspections.PhpInspection;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TestCase
    extends BasePlatformTestCase {
    private static final int classBaseLength = "net.rentalhost.plugins.php.hammer".length();

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        myFixture.setTestDataPath((new File("src/test/resources")).getAbsolutePath());
    }

    protected <T extends PhpInspection> void testInspection(
        @NotNull final Class<T> inspectionClass
    ) {
        testInspection(inspectionClass, null);
    }

    protected <T extends PhpInspection> void testInspection(
        @NotNull final Class<T> inspectionClass,
        @Nullable final String phpSourceSubName
    ) {
        final String phpSource = inspectionClass.getName().substring(classBaseLength + 1).replace(".", "/");
        final String phpSourceNamed = phpSourceSubName == null
                                      ? phpSource
                                      : phpSource + "-" + phpSourceSubName;

        try {
            myFixture.enableInspections(inspectionClass.getDeclaredConstructor().newInstance());
        }
        catch (final InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        myFixture.configureByFile(phpSourceNamed + ".php");
        myFixture.testHighlighting(true, false, true);

        final File phpSourceFixedFile = new File(phpSourceNamed + ".fixed.php");

        if (phpSourceFixedFile.exists()) {
            myFixture.getAllQuickFixes().forEach(fix -> myFixture.launchAction(fix));
            myFixture.checkResultByFile("inspections/codeStyle/NullableTypesFormatInspection.fixed.php");
        }
    }
}
