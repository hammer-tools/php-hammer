package net.rentalhost.plugins.php.hammer.inspections.codeError

import com.intellij.codeInspection.ProblemsHolder
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.FunctionReference
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression
import com.jetbrains.php.lang.psi.elements.Variable
import com.jetbrains.php.lang.psi.elements.impl.ArrayCreationExpressionImpl
import com.jetbrains.php.lang.psi.resolve.types.PhpType
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.hammer.extensions.psi.getTypes
import net.rentalhost.plugins.hammer.extensions.psi.isName
import net.rentalhost.plugins.hammer.services.ElementService
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService
import kotlin.streams.toList

class CompactArgumentInvalidInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        override fun visitPhpFunctionCall(element: FunctionReference) {
            if (!element.isName("compact"))
                return

            for (parameter in element.parameters) {
                val values = when (parameter) {
                    is StringLiteralExpression -> continue
                    is ArrayCreationExpressionImpl -> parameter.values().toList()
                    else -> listOf(parameter)
                }

                values
                    .filter { it !is StringLiteralExpression }
                    .forEach {
                        if (it is Variable) {
                            val variableTypes = it.getTypes()

                            if (variableTypes.size != 1 ||
                                variableTypes[0] == PhpType._STRING) {
                                return@forEach
                            }
                        }

                        ProblemsHolderService.instance.registerProblem(
                            problemsHolder, it,
                            "invalid argument for compact() function",
                            QuickFixService.instance.simpleInline("Drop invalid term") {
                                ElementService.dropCompactArgument(it)
                            }
                        )
                    }
            }
        }
    }
}
