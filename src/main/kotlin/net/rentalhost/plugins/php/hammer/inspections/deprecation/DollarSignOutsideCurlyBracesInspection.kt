package net.rentalhost.plugins.php.hammer.inspections.deprecation

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.impl.StringLiteralExpressionImpl
import com.jetbrains.php.lang.psi.elements.impl.VariableImpl
import net.rentalhost.plugins.services.FactoryService
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.ProblemsHolderService

class DollarSignOutsideCurlyBracesInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object: PsiElementVisitor() {
        override fun visitElement(element: PsiElement) {
            if (element is VariableImpl) {
                val parent = element.parent

                if (parent is StringLiteralExpressionImpl) {
                    val elementCurly = element.text

                    if (elementCurly.startsWith("\${") &&
                        elementCurly.endsWith("}")) {
                        val replaceWith = elementCurly.substring(2, elementCurly.length - 1)

                        ProblemsHolderService.registerProblem(
                            problemsHolder,
                            element,
                            "Deprecated: using \${var} in strings.",
                            LocalQuickFixService.SimpleReplaceQuickFix(
                                "Replace with {\$$replaceWith}",
                                FactoryService.createCurlyVariable(problemsHolder.project, replaceWith)
                            )
                        )
                    }
                }
            }
        }
    }
}
