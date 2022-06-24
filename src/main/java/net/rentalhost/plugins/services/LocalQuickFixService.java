package net.rentalhost.plugins.services;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.openapi.project.Project;
import com.jetbrains.php.lang.psi.PhpPsiElementFactory;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.elements.PhpFieldType;
import com.jetbrains.php.lang.psi.elements.PhpParameterType;
import com.jetbrains.php.lang.psi.elements.PhpReturnType;
import com.jetbrains.php.lang.psi.elements.PhpTypeDeclaration;
import com.jetbrains.php.lang.psi.elements.impl.FunctionImpl;

import java.util.Arrays;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;

public class LocalQuickFixService {
    public static void replaceType(
        @NotNull final Project project,
        @NotNull final PhpTypeDeclaration element,
        @NotNull final String typeReplacement
    ) {
        PhpTypeDeclaration elementReplacement = null;

        if (element instanceof PhpReturnType) {
            elementReplacement = PhpPsiElementFactory
                .createPhpPsiFromText(project, FunctionImpl.class, String.format("function dummy(): %s {}", typeReplacement))
                .getReturnType();
        }
        else if (element instanceof PhpParameterType) {
            elementReplacement = Objects.requireNonNull(
                PhpPsiElementFactory
                    .createPhpPsiFromText(project, FunctionImpl.class, String.format("function dummy(%s $dummy) {}", typeReplacement))
                    .getParameter(0)
            ).getTypeDeclaration();
        }
        else if (element instanceof PhpFieldType) {
            final var field = Arrays.stream(PhpPsiElementFactory
                                                .createPhpPsiFromText(project, PhpClass.class, String.format("class Dummy { public %s $dummy; }", typeReplacement))
                                                .getOwnFields())
                                    .findFirst();

            if (field.isEmpty()) {
                return;
            }

            elementReplacement = field.get()
                                      .getTypeDeclaration();
        }

        if (elementReplacement == null) {
            return;
        }

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
