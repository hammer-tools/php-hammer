package net.rentalhost.plugins.php.hammer.services;

import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.project.Project;
import com.jetbrains.php.lang.psi.PhpPsiElementFactory;
import com.jetbrains.php.lang.psi.elements.PhpTypeDeclaration;
import com.jetbrains.php.lang.psi.elements.impl.FunctionImpl;

import org.jetbrains.annotations.NotNull;

public class LocalQuickFixService {
    public static void replaceType(
        @NotNull final Project project,
        @NotNull final ProblemDescriptor descriptor,
        @NotNull final String typeReplacement
    ) {
        final var element = (PhpTypeDeclaration) descriptor.getPsiElement();

        final var elementReplacement = PhpPsiElementFactory
            .createPhpPsiFromText(project, FunctionImpl.class, String.format("function dummy(): %s {}", typeReplacement))
            .getReturnType();

        assert (elementReplacement != null);

        element.replace(elementReplacement);
    }
}
