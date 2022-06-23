package net.rentalhost.plugins.php.hammer.inspections.codeStyle;

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.jetbrains.php.lang.psi.elements.ClassReference;
import com.jetbrains.php.lang.psi.elements.PhpTypeDeclaration;

import org.jetbrains.annotations.NotNull;

import net.rentalhost.plugins.php.hammer.services.LocalQuickFixService;
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService;
import net.rentalhost.plugins.php.hammer.services.TypeService;

public class NormalizeNativeTypesCaseInspection
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

                    if (elementTypeText == null) {
                        return;
                    }

                    final var elementTypeTextLowercase = elementTypeText.toLowerCase();

                    if (elementTypeText.equals(elementTypeTextLowercase) ||
                        !TypeService.nativeTypes.contains(elementTypeTextLowercase)) {
                        return;
                    }

                    final var elementParent = element.getParent();

                    if (!(elementParent instanceof PhpTypeDeclaration)) {
                        return;
                    }

                    final var elementTypeReplacementSuggestion = TypeService.joinTypesStream(
                        TypeService.listTypes(elementParent.getText())
                                   .map(s -> s.trim().equals(elementTypeText) ? elementTypeTextLowercase : s)
                    );

                    ProblemsHolderService.registerProblem(
                        problemsHolder,
                        element,
                        String.format("Type format must be \"%s\".", elementTypeTextLowercase),
                        new LocalQuickFixService.SimpleTypeReplaceQuickFix(
                            elementTypeReplacementSuggestion,
                            String.format("Replace it with \"%s\"", elementTypeTextLowercase),
                            true)
                    );
                }
            }
        };
    }
}
