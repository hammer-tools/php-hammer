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

import java.util.regex.Pattern;

import org.jetbrains.annotations.NotNull;

import net.rentalhost.plugins.php.hammer.services.LocalQuickFixService;
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService;

public class NullableTypeRightmostInspection
    extends PhpInspection {
    Pattern expressionNull = Pattern.compile("(?<=\\||^)\\\\?null\\|");

    @Override
    public @NotNull PsiElementVisitor buildVisitor(
        @NotNull final ProblemsHolder problemsHolder,
        final boolean isOnTheFly
    ) {
        return new PsiElementVisitor() {
            @Override
            public void visitElement(@NotNull final PsiElement element) {
                if (element instanceof PhpTypeDeclaration) {
                    final var elementType     = ((PhpTypeDeclaration) element).getType();
                    final var elementTypeText = element.getText();

                    if (!elementTypeText.startsWith("?")) {
                        final var elementTypes = elementType.getTypes();

                        if (elementTypes.contains("\\null")) {
                            final var elementTypeTextMatcher = expressionNull.matcher(elementTypeText);

                            if (elementTypeTextMatcher.find()) {
                                final var elementTypeReplacementSuggestion = elementTypeTextMatcher.replaceFirst("") + "|null";

                                ProblemsHolderService.registerProblem(
                                    problemsHolder,
                                    element,
                                    String.format("Nullable type must be on rightmost side as \"%s\".", elementTypeReplacementSuggestion),
                                    new NullableTypeRightmostFix(elementTypeReplacementSuggestion)
                                );
                            }
                        }
                    }
                }
            }
        };
    }

    private static final class NullableTypeRightmostFix
        implements LocalQuickFix {
        private final String elementReplacementText;

        public NullableTypeRightmostFix(final String elementReplacementText) {
            this.elementReplacementText = elementReplacementText;
        }

        @Override
        public @IntentionFamilyName @NotNull String getFamilyName() {
            return "Move \"null\" type to rightmost side";
        }

        @Override
        public void applyFix(
            @NotNull final Project project,
            @NotNull final ProblemDescriptor descriptor
        ) {
            LocalQuickFixService.replaceType(project, descriptor, this.elementReplacementText);
        }
    }
}
