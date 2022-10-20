package net.rentalhost.plugins.php.hammer.inspections.codeError

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.util.PsiTreeUtil
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.Function
import com.jetbrains.php.lang.psi.elements.FunctionReference
import com.jetbrains.php.lang.psi.elements.impl.StringLiteralExpressionImpl
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.hammer.extensions.psi.isName
import net.rentalhost.plugins.hammer.extensions.psi.isShortFunction
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService

class CompactInsideShortFunctionInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        override fun visitPhpFunctionCall(element: FunctionReference) {
            if (!element.isName("compact"))
                return

            val elementScopeFunction = PsiTreeUtil.getParentOfType(element, Function::class.java) ?: return

            if (!elementScopeFunction.isShortFunction())
                return

            val allowedNames = elementScopeFunction.parameters.map { parameter -> parameter.name }

            element.parameters.forEach { parameter ->
                if (parameter !is StringLiteralExpressionImpl ||
                    !allowedNames.contains(parameter.contents)) {
                    ProblemsHolderService.instance.registerProblem(
                        problemsHolder,
                        element,
                        "usage of compact() inside a short function"
                    )
                }
            }
        }
    }
}
