package net.rentalhost.plugins.php.hammer.inspections.codeStyle;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.jetbrains.php.lang.inspections.PhpInspection;
import com.jetbrains.php.lang.psi.elements.PhpTypeDeclaration;

import javax.swing.*;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.rentalhost.plugins.php.hammer.services.LocalQuickFixService;
import net.rentalhost.plugins.php.hammer.services.OptionsPanelService;
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService;
import net.rentalhost.plugins.php.hammer.services.TypeService;

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
                if (element instanceof PhpTypeDeclaration) {
                    final var elementType  = ((PhpTypeDeclaration) element).getType();
                    final var elementTypes = elementType.getTypes();

                    if (elementTypes.size() == 2 &&
                        elementTypes.contains("\\null")) {
                        final var elementTypeText    = element.getText();
                        final var elementTypeIsShort = elementTypeText.startsWith("?");

                        String elementTypeReplacementSuggestion = null;

                        if (elementTypeIsShort && FORMAT_LONG) {
                            elementTypeReplacementSuggestion =
                                String.format("%s|null", elementTypeText.substring(1));
                        }
                        else if (!elementTypeIsShort && FORMAT_SHORT) {
                            final var elementTypeSingular = TypeService.listNonNullableTypes(elementTypeText).findFirst();

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
                                new NullableTypeFormatFix(elementTypeReplacementSuggestion, FORMAT_LONG)
                            );
                        }
                    }
                }
            }
        };
    }

    @Override
    public @Nullable JComponent createOptionsPanel() {
        return OptionsPanelService.create(component -> component.delegateRadioCreation(radioComponent -> {
            radioComponent.addOption("Use short format (\"?int\")", FORMAT_SHORT, isSelected -> FORMAT_SHORT = isSelected);
            radioComponent.addOption("Use long format (\"int|null\")", FORMAT_LONG, isSelected -> FORMAT_LONG = isSelected);
        }));
    }

    private static final class NullableTypeFormatFix
        implements LocalQuickFix {
        private final String elementReplacementText;

        private final boolean toLongFormat;

        public NullableTypeFormatFix(
            final String elementReplacementText,
            final boolean toLongFormat
        ) {
            this.elementReplacementText = elementReplacementText;
            this.toLongFormat = toLongFormat;
        }

        @Override
        public @IntentionFamilyName @NotNull String getFamilyName() {
            return toLongFormat
                   ? "Replace with the long format"
                   : "Replace with the short format";
        }

        @Override
        public void applyFix(
            @NotNull final Project project,
            @NotNull final ProblemDescriptor descriptor
        ) {
            LocalQuickFixService.replaceType(project, (PhpTypeDeclaration) descriptor.getPsiElement(), this.elementReplacementText);
        }
    }
}
