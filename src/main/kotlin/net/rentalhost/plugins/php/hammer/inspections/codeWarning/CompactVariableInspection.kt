package net.rentalhost.plugins.php.hammer.inspections.codeWarning

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.codeInspection.options.OptCheckbox
import com.intellij.codeInspection.options.OptPane
import com.intellij.codeInspection.options.PlainMessage
import com.intellij.openapi.util.text.HtmlChunk
import com.intellij.refactoring.suggested.createSmartPointer
import com.intellij.util.xmlb.annotations.OptionTag
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.FunctionReference
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression
import com.jetbrains.php.lang.psi.elements.Variable
import com.jetbrains.php.lang.psi.elements.impl.ArrayCreationExpressionImpl
import com.jetbrains.php.lang.psi.resolve.types.PhpType
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.extensions.psi.getTypes
import net.rentalhost.plugins.php.hammer.extensions.psi.isName
import net.rentalhost.plugins.php.hammer.services.FactoryService
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService

class CompactVariableInspection : PhpInspection() {
    @OptionTag
    var includeStrings: Boolean = true

    @OptionTag
    var includeArrays: Boolean = true

    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object : PhpElementVisitor() {
        override fun visitPhpFunctionCall(element: FunctionReference) {
            if (!element.isName("\\compact"))
                return

            for (parameter in element.parameters) {
                val values = when (parameter) {
                    is StringLiteralExpression -> continue
                    is ArrayCreationExpressionImpl -> parameter.values().toList()
                    else -> listOf(parameter)
                }

                values
                    .filterIsInstance<Variable>()
                    .forEach {
                        val variableTypes by lazy { it.getTypes() }

                        if (!includeStrings) {
                            if (variableTypes.size == 1 &&
                                variableTypes[0] === PhpType._STRING
                            ) {
                                return@forEach
                            }
                        }

                        if (!includeArrays) {
                            if (variableTypes.size == 1 &&
                                variableTypes[0] === PhpType._ARRAY
                            ) {
                                return@forEach
                            }
                        }

                        ProblemsHolderService.instance.registerProblem(
                            problemsHolder, it,
                            "variables should be avoided in compact()",
                            QuickFixService.instance.simpleReplace(
                                "Replace with string",
                                FactoryService.createStringLiteral(problemsHolder.project, it.name).createSmartPointer()
                            )
                        )
                    }
            }
        }
    }

    override fun getOptionsPane(): OptPane {
        return OptPane.pane(
            OptCheckbox(
                "includeStrings",
                PlainMessage("Include string-type"),
                emptyList(),
                HtmlChunk.raw(
                    "This option allows this inspection to consider variables that store a value of type string, " +
                            "which can represent the name of a variable that will be packed by <code>compact()</code>. " +
                            "However, as this is not very common, this option is enabled by default."
                )
            ),

            OptCheckbox(
                "includeArrays",
                PlainMessage("Include array-type"),
                emptyList(),
                HtmlChunk.raw(
                    "This option allows this inspection to consider variables that store a value of type array, " +
                            "which can represent the names of variables that will be packed by <code>compact()</code>. " +
                            "However, as this is not very common, this option is enabled by default."
                )
            )
        )
    }
}
