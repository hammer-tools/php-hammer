package net.rentalhost.plugins.php.hammer.inspections.codeStyle;

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.jetbrains.php.lang.psi.elements.ClassReference;
import com.jetbrains.php.lang.psi.elements.PhpTypeDeclaration;

import org.jetbrains.annotations.NotNull;

import net.rentalhost.plugins.php.hammer.services.LocalQuickFixService;
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService;
import net.rentalhost.plugins.php.hammer.services.TypeService;

public class NormalizeNativeTypesNamespaceInspection
    extends LocalInspectionTool {
    @Override
    public @NotNull PsiElementVisitor buildVisitor(
        @NotNull final ProblemsHolder problemsHolder,
        final boolean isOnTheFly
    ) {
        return new PsiElementVisitor() {
            @Override
            public void visitElement(@NotNull final PsiElement element) {
                if (element instanceof ClassReference) {
                    final var elementTypeText = element.getText();

                    if (elementTypeText == null ||
                        !elementTypeText.startsWith("\\") ||
                        !TypeService.nativeTypes.contains(elementTypeText.toLowerCase())) {
                        return;
                    }

                    final var elementParent = element.getParent();

                    if (!(elementParent instanceof PhpTypeDeclaration)) {
                        return;
                    }

                    final var elementTypeExpectedFinal = elementTypeText.substring(1);
                    final var elementTypeReplacementSuggestion = TypeService.joinTypesStream(
                        TypeService.listTypes(elementParent.getText())
                                   .map(s -> s.trim().equals(elementTypeText) ? elementTypeExpectedFinal : s)
                    );

                    ProblemsHolderService.registerProblem(
                        problemsHolder,
                        element,
                        String.format("Type format must be \"%s\".", elementTypeExpectedFinal),
                        new NormalizeNativeTypesNamespaceFix(elementTypeReplacementSuggestion, elementTypeExpectedFinal)
                    );
                }
            }
        };
    }

    private static final class NormalizeNativeTypesNamespaceFix
        implements LocalQuickFix {
        private final String elementReplacementText;
        private final String elementTypeExpected;

        public NormalizeNativeTypesNamespaceFix(
            final String elementReplacementText,
            final String elementTypeExpected
        ) {
            this.elementReplacementText = elementReplacementText;
            this.elementTypeExpected = elementTypeExpected;
        }

        @Override
        public @IntentionFamilyName @NotNull String getFamilyName() {
            return String.format("Replace with \"%s\"", this.elementTypeExpected);
        }

        @Override
        public void applyFix(
            @NotNull final Project project,
            @NotNull final ProblemDescriptor descriptor
        ) {
            LocalQuickFixService.replaceType(project, (PhpTypeDeclaration) descriptor.getPsiElement().getParent(), this.elementReplacementText);
        }
    }
}
