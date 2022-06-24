package net.rentalhost.plugins.services;

import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.jetbrains.php.config.PhpLanguageLevel;
import com.jetbrains.php.config.PhpProjectConfigurationFacade;
import com.jetbrains.php.lang.inspections.PhpInspection;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class TestCase
    extends BasePlatformTestCase {
    private static final int classBaseLength = "net.rentalhost.plugins.php.hammer".length();

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        myFixture.setTestDataPath((new File("src/test/resources")).getAbsolutePath());
    }

    protected void testInspection(
        @NotNull final Class<? extends PhpInspection> inspectionClass
    ) {
        testInspection(inspectionClass, null);
    }

    protected void testInspection(
        @NotNull final Class<? extends PhpInspection> inspectionClass,
        @Nullable final String phpSourceSubName
    ) {
        final String phpSource = inspectionClass.getName().substring(classBaseLength + 1).replace(".", "/");
        final String phpSourceNamed = phpSourceSubName == null
                                      ? phpSource
                                      : phpSource + "-" + phpSourceSubName;

        final PhpInspection phpInspection;

        try {
            phpInspection = inspectionClass.getDeclaredConstructor().newInstance();
        }
        catch (final InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        PhpProjectConfigurationFacade.getInstance(getProject()).setLanguageLevel(PhpLanguageLevel.PHP800);

        myFixture.enableInspections(phpInspection);
        myFixture.configureByFile(phpSourceNamed + ".php");
        myFixture.testHighlighting(true, false, true);

        final File phpSourceFixedFile = new File(String.format("src/test/resources/%s.fixed.php", phpSourceNamed));

        if (phpSourceFixedFile.exists()) {
            myFixture.getAllQuickFixes().forEach(fix -> myFixture.launchAction(fix));
            myFixture.checkResultByFile(phpSourceNamed + ".fixed.php");
        }
    }
}
