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

public class NormalizeNativeTypesInspection
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

                    String elementTypeExpected = null;

                    switch (elementTypeText) {
                        case "integer":
                        case "\\integer":
                            elementTypeExpected = "int";
                            break;

                        case "boolean":
                        case "\\boolean":
                            elementTypeExpected = "bool";
                            break;

                        case "double":
                        case "\\double":
                            elementTypeExpected = "float";
                            break;
                    }

                    if (elementTypeExpected == null) {
                        return;
                    }

                    final var elementParent = element.getParent();

                    if (!(elementParent instanceof PhpTypeDeclaration)) {
                        return;
                    }

                    final var elementTypeExpectedFinal = elementTypeExpected;
                    final var elementTypeReplacementSuggestion = TypeService.joinTypesStream(
                        TypeService.listTypes(elementParent.getText())
                                   .map(s -> s.trim().equals(elementTypeText) ? elementTypeExpectedFinal : s)
                    );

                    ProblemsHolderService.registerProblem(
                        problemsHolder,
                        element,
                        String.format("Type must be normalized (\"%s\").", elementTypeExpected),
                        new LocalQuickFixService.SimpleTypeReplaceQuickFix(
                            elementTypeReplacementSuggestion,
                            String.format("Replace it with \"%s\"", elementTypeExpected),
                            true
                        )
                    );
                }
            }
        };
    }
}
