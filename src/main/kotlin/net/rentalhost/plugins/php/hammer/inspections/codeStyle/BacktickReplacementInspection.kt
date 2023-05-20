package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.refactoring.suggested.createSmartPointer
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.impl.PhpShellCommandExpressionImpl
import com.jetbrains.php.lang.psi.elements.impl.VariableImpl
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.services.FactoryService
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService
import net.rentalhost.plugins.php.hammer.services.StringService

class BacktickReplacementInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        override fun visitElement(element: PsiElement) {
            if (element !is PhpShellCommandExpressionImpl)
                return

            val commandContents = element.text.substring(1, element.text.length - 1)
            val commandVariable = PsiTreeUtil.findChildrenOfType(element, VariableImpl::class.java).toList()

            val containsVariableOnly = commandVariable.size == 1 &&
                                       commandVariable[0].prevSibling.text == "`" &&
                                       commandVariable[0].nextSibling.text == "`"

            ProblemsHolderService.instance.registerProblem(
                problemsHolder,
                element,
                "backtick operator can be replaced by shell_exec()",
                QuickFixService.instance.simpleReplace(
                    "Replace by shell_exec()",
                    FactoryService.createFunctionCall(
                        problemsHolder.project, "shell_exec",
                        if (containsVariableOnly) listOf("\$${commandVariable.first().name}")
                        else listOf(StringService.addQuotes(commandContents, commandVariable.isNotEmpty(), false))
                    ).createSmartPointer()
                )
            )
        }
    }
}
