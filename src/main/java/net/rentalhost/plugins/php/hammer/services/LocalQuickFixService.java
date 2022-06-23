package net.rentalhost.plugins.php.hammer.services;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.openapi.project.Project;
import com.jetbrains.php.lang.psi.PhpPsiElementFactory;
import com.jetbrains.php.lang.psi.elements.PhpTypeDeclaration;
import com.jetbrains.php.lang.psi.elements.impl.FunctionImpl;

import org.jetbrains.annotations.NotNull;

public class LocalQuickFixService {
    public static void replaceType(
        @NotNull final Project project,
        @NotNull final PhpTypeDeclaration element,
        @NotNull final String typeReplacement
    ) {
        final var elementReplacement = PhpPsiElementFactory
            .createPhpPsiFromText(project, FunctionImpl.class, String.format("function dummy(): %s {}", typeReplacement))
            .getReturnType();

        assert (elementReplacement != null);

        element.replace(elementReplacement);
    }

    public static final class SimpleTypeReplaceWithParentQuickFix
        implements LocalQuickFix {
        private final String entireTypesReplacement;
        private final String typeReplacementDescription;

        public SimpleTypeReplaceWithParentQuickFix(
            final String entireTypesReplacement,
            final String typeReplacementDescription
        ) {
            this.entireTypesReplacement = entireTypesReplacement;
            this.typeReplacementDescription = typeReplacementDescription;
        }

        @Override
        public @IntentionFamilyName @NotNull String getFamilyName() {
            return String.format("Replace it with \"%s\"", this.typeReplacementDescription);
        }

        @Override
        public void applyFix(
            @NotNull final Project project,
            @NotNull final ProblemDescriptor descriptor
        ) {
            LocalQuickFixService.replaceType(project, (PhpTypeDeclaration) descriptor.getPsiElement().getParent(), this.entireTypesReplacement);
        }
    }
}
