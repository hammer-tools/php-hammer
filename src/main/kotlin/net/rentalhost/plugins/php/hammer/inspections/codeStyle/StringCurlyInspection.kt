package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression
import com.jetbrains.php.lang.psi.elements.Variable
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.services.FactoryService
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.ProblemsHolderService

class StringCurlyInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        override fun visitPhpVariable(element: Variable) {
            val elementParent = element.parent

            if (elementParent !is StringLiteralExpression ||
                element.text != "\$${element.name}")
                return

            ProblemsHolderService.registerProblem(
                problemsHolder,
                element,
                "variable must have curly braces",
                LocalQuickFixService.SimpleReplaceQuickFix(
                    "Wrap with curly braces",
                    FactoryService.createCurlyVariable(problemsHolder.project, element.name)
                )
            )
        }
    }
}
