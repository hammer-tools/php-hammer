package net.rentalhost.plugins.php.hammer.inspections.codeStyle;

import com.google.common.collect.Iterables;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.jetbrains.php.config.PhpLanguageLevel;
import com.jetbrains.php.lang.inspections.PhpInspection;
import com.jetbrains.php.lang.psi.elements.impl.PhpTypeDeclarationImpl;
import com.jetbrains.php.lang.psi.resolve.types.PhpType;

import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.rentalhost.plugins.services.LocalQuickFixService;
import net.rentalhost.plugins.services.ProblemsHolderService;
import net.rentalhost.plugins.services.TypeService;

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
                if (element instanceof PhpTypeDeclarationImpl) {
                    final var elementTypeText = element.getText();

                    if (!elementTypeText.startsWith("?")) {
                        final var elementTypes = TypeService.splitTypes(elementTypeText)
                                                            .map(s -> s.equals("null") ? PhpType._NULL : s)
                                                            .collect(Collectors.toList());

                        if (elementTypes.contains(PhpType._NULL) &&
                            !Iterables.getLast(elementTypes).equals(PhpType._NULL)) {
                            final var elementTypeReplacementSuggestion = TypeService.joinTypesStream(TypeService.exceptNull(elementTypeText)) + "|null";

                            ProblemsHolderService.registerProblem(
                                problemsHolder,
                                element,
                                String.format("Nullable type must be on rightmost side (\"%s\").", elementTypeReplacementSuggestion),
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

    @Override
    protected @Nullable PhpLanguageLevel getMinimumSupportedLanguageLevel() {
        return PhpLanguageLevel.PHP800;
    }
}
