package net.rentalhost.plugins.php.hammer.inspections.codeError

import com.intellij.codeInspection.ProblemsHolder
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.Function
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.extensions.psi.*
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.ProblemsHolderService

class StaticAnonymousFunctionCannotAccessThisInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        override fun visitPhpFunction(element: Function) {
            if (!element.isAnonymous() ||
                !element.isStatic())
                return

            for (elementScope in element.scopes()) {
                if (elementScope.accessVariables().find { it.variableName == "this" } == null)
                    continue

                ProblemsHolderService.registerProblem(
                    problemsHolder,
                    element.firstChild,
                    "static anonymous functions cannot access \$this",
                    LocalQuickFixService.SimpleDeleteQuickFix("Delete this \"static\" declaration")
                )

                return
            }
        }
    }
}
