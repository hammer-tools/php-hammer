package net.rentalhost.plugins.php.hammer.inspections.codeStyle;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.jetbrains.php.config.PhpLanguageLevel;
import com.jetbrains.php.lang.inspections.PhpInspection;
import com.jetbrains.php.lang.psi.elements.PhpTypeDeclaration;
import com.jetbrains.php.lang.psi.elements.impl.PhpTypeDeclarationImpl;
import com.jetbrains.php.lang.psi.resolve.types.PhpType;

import javax.swing.*;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.rentalhost.plugins.services.LocalQuickFixService;
import net.rentalhost.plugins.services.OptionsPanelService;
import net.rentalhost.plugins.services.ProblemsHolderService;
import net.rentalhost.plugins.services.TypeService;

public class NullableTypesFormatInspection
    extends PhpInspection {
    public boolean FORMAT_SHORT = false;
    public boolean FORMAT_LONG  = true;

    @Override
    public @NotNull PsiElementVisitor buildVisitor(
        @NotNull final ProblemsHolder problemsHolder,
        final boolean isOnTheFly
    ) {
        return new PsiElementVisitor() {
            @Override
            public void visitElement(@NotNull final PsiElement element) {
                if (element instanceof PhpTypeDeclarationImpl) {
                    final var elementType  = ((PhpTypeDeclaration) element).getType();
                    final var elementTypes = elementType.getTypes();

                    if (elementTypes.size() == 2 &&
                        elementTypes.contains(PhpType._NULL)) {
                        final var elementTypeText    = element.getText();
                        final var elementTypeIsShort = elementTypeText.startsWith("?");

                        String elementTypeReplacementSuggestion = null;

                        if (elementTypeIsShort && FORMAT_LONG) {
                            elementTypeReplacementSuggestion =
                                String.format("%s|null", elementTypeText.substring(1));
                        }
                        else if (!elementTypeIsShort && FORMAT_SHORT) {
                            final var elementTypeSingular = TypeService.exceptNull(elementTypeText).findFirst();

                            if (elementTypeSingular.isPresent()) {
                                elementTypeReplacementSuggestion =
                                    String.format("?%s", elementTypeSingular.get());
                            }
                        }

                        if (elementTypeReplacementSuggestion != null) {
                            ProblemsHolderService.registerProblem(
                                problemsHolder,
                                element,
                                String.format("Nullable type must be written as \"%s\".", elementTypeReplacementSuggestion),
                                new LocalQuickFixService.SimpleTypeReplaceQuickFix(
                                    elementTypeReplacementSuggestion,
                                    FORMAT_LONG ? "Replace with the long format" : "Replace with the short format"
                                )
                            );
                        }
                    }
                }
            }
        };
    }

    @Override
    protected @Nullable PhpLanguageLevel getMinimumSupportedLanguageLevel() {
        return PhpLanguageLevel.PHP800;
    }

    @Override
    public @Nullable JComponent createOptionsPanel() {
        return OptionsPanelService.create(component -> component.delegateRadioCreation(radioComponent -> {
            radioComponent.addOption("Use short format (\"?int\")", FORMAT_SHORT, isSelected -> FORMAT_SHORT = isSelected);
            radioComponent.addOption("Use long format (\"int|null\")", FORMAT_LONG, isSelected -> FORMAT_LONG = isSelected);
        }));
    }
}
