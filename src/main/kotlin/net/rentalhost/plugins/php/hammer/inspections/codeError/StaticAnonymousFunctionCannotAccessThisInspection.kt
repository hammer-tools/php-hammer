package net.rentalhost.plugins.php.hammer.inspections.codeError

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.impl.FunctionImpl
import net.rentalhost.plugins.extensions.psi.*
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.ProblemsHolderService

class StaticAnonymousFunctionCannotAccessThisInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object: PsiElementVisitor() {
        override fun visitElement(element: PsiElement) {
            if (element is FunctionImpl &&
                element.isAnonymous() &&
                element.isStatic()) {
                for (elementScope in element.scopes()) {
                    if (elementScope.accessVariables().find { it.variableName == "this" } == null)
                        continue

                    ProblemsHolderService.registerProblem(
                        problemsHolder,
                        element.firstChild,
                        "Static anonymous functions cannot access \$this.",
                        LocalQuickFixService.SimpleDeleteQuickFix("Delete this \"static\" declaration")
                    )

                    return
                }
            }
        }
    }
}
