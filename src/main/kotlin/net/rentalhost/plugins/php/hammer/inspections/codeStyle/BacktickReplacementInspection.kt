package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.impl.PhpShellCommandExpressionImpl
import com.jetbrains.php.lang.psi.elements.impl.VariableImpl
import net.rentalhost.plugins.services.FactoryService
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.ProblemsHolderService
import net.rentalhost.plugins.services.StringService

class BacktickReplacementInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object: PsiElementVisitor() {
        override fun visitElement(element: PsiElement) {
            if (element !is PhpShellCommandExpressionImpl)
                return

            val commandContents = element.text.substring(1, element.text.length - 1)
            val commandVariable = PsiTreeUtil.findChildrenOfType(element, VariableImpl::class.java).toList()

            val containsVariableOnly = commandVariable.size == 1 &&
                                       commandVariable[0].prevSibling.text == "`" &&
                                       commandVariable[0].nextSibling.text == "`"

            ProblemsHolderService.registerProblem(
                problemsHolder,
                element,
                "Backtick operator can be replaced by shell_exec().",
                LocalQuickFixService.SimpleReplaceQuickFix(
                    "Replace by shell_exec()",
                    FactoryService.createFunctionCall(
                        problemsHolder.project, "shell_exec",
                        if (containsVariableOnly) listOf("\$${commandVariable.first().name}")
                        else listOf(StringService.addQuotes(commandContents, commandVariable.isNotEmpty(), false))
                    )
                )
            )
        }
    }
}
