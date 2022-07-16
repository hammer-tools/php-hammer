package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression
import com.jetbrains.php.lang.psi.elements.impl.VariableImpl
import net.rentalhost.plugins.services.FactoryService
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.ProblemsHolderService

class StringCurlyInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object: PsiElementVisitor() {
        override fun visitElement(element: PsiElement) {
            if (element !is VariableImpl)
                return

            val elementParent = element.parent

            if (elementParent !is StringLiteralExpression ||
                element.text != "\$${element.name}")
                return

            ProblemsHolderService.registerProblem(
                problemsHolder,
                element,
                "Variable must have curly braces.",
                LocalQuickFixService.SimpleReplaceQuickFix(
                    "Wrap with curly braces",
                    FactoryService.createCurlyVariable(problemsHolder.project, element.name)
                )
            )
        }
    }
}
