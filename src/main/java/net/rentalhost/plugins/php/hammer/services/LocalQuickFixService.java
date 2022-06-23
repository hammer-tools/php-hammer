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

    public static final class SimpleTypeReplaceQuickFix
        implements LocalQuickFix {
        private final String  entireTypesReplacement;
        private final String  quickFixTitle;
        private final boolean considerParent;

        public SimpleTypeReplaceQuickFix(
            final String entireTypesReplacement,
            final String quickFixTitle
        ) {
            this(entireTypesReplacement, quickFixTitle, false);
        }

        public SimpleTypeReplaceQuickFix(
            final String entireTypesReplacement,
            final String quickFixTitle,
            final boolean considerParent
        ) {
            this.entireTypesReplacement = entireTypesReplacement;
            this.quickFixTitle = quickFixTitle;
            this.considerParent = considerParent;
        }

        @Override
        public @IntentionFamilyName @NotNull String getFamilyName() {
            return quickFixTitle;
        }

        @Override
        public void applyFix(
            @NotNull final Project project,
            @NotNull final ProblemDescriptor descriptor
        ) {
            final var descriptionElement = considerParent
                                           ? (PhpTypeDeclaration) descriptor.getPsiElement().getParent()
                                           : (PhpTypeDeclaration) descriptor.getPsiElement();

            LocalQuickFixService.replaceType(project, descriptionElement, entireTypesReplacement);
        }
    }
}
