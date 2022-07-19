package net.rentalhost.plugins.php.hammer.inspections.codeWarning

import com.intellij.codeInspection.ProblemsHolder
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.FunctionReference
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression
import com.jetbrains.php.lang.psi.elements.Variable
import com.jetbrains.php.lang.psi.elements.impl.ArrayCreationExpressionImpl
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.extensions.psi.isName
import net.rentalhost.plugins.services.ProblemsHolderService
import kotlin.streams.toList

class CompactVariableInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        override fun visitPhpFunctionCall(element: FunctionReference) {
            if (!element.isName("compact"))
                return

            for (parameter in element.parameters) {
                val values =
                    if (parameter is StringLiteralExpression) continue
                    else if (parameter is ArrayCreationExpressionImpl) parameter.values().toList()
                    else listOf(parameter)

                values
                    .filterIsInstance(Variable::class.java)
                    .forEach {
                        ProblemsHolderService.registerProblem(
                            problemsHolder, it,
                            "Variables should be avoided in compact()."
                        )
                    }
            }
        }
    }
}
