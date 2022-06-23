package net.rentalhost.plugins.php.hammer.inspections.codeStyle;

import com.google.common.collect.Iterables;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.jetbrains.php.lang.inspections.PhpInspection;
import com.jetbrains.php.lang.psi.elements.PhpTypeDeclaration;
import com.jetbrains.php.lang.psi.resolve.types.PhpType;

import org.jetbrains.annotations.NotNull;

import net.rentalhost.plugins.php.hammer.services.LocalQuickFixService;
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService;
import net.rentalhost.plugins.php.hammer.services.TypeService;

public class NullableTypeRightmostInspection
    extends PhpInspection {
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

                        if (elementTypes.contains(PhpType._NULL) &&
                            !Iterables.getLast(elementTypes).equals(PhpType._NULL)) {
                            final var elementTypeReplacementSuggestion = TypeService.joinTypesStream(TypeService.listNonNullableTypes(elementTypeText)) + "|null";

                            ProblemsHolderService.registerProblem(
                                problemsHolder,
                                element,
                                String.format("Nullable type must be on rightmost side as \"%s\".", elementTypeReplacementSuggestion),
                                new LocalQuickFixService.SimpleTypeReplaceQuickFix(
                                    elementTypeReplacementSuggestion,
                                    "Move \"null\" type to rightmost side"
                                )
                            );
                        }
                    }
                }
            }
        };
    }
}
