package net.rentalhost.plugins.services;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElement;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ProblemsHolderService {
    public static void registerProblem(
        @NotNull final ProblemsHolder problemsHolder,
        @NotNull final PsiElement element,
        @NotNull final String descriptionTemplate,
        @Nullable final LocalQuickFix localQuickFix
    ) {
        problemsHolder.registerProblem(
            element,
            String.format("[PHP Hammer] %s", descriptionTemplate),
            localQuickFix
        );
    }
}
