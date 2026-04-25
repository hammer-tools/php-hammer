package net.rentalhost.plugins.php.hammer.inspections.codeWarning

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.codeInspection.options.OptCheckbox
import com.intellij.codeInspection.options.OptPane
import com.intellij.codeInspection.options.PlainMessage
import com.intellij.openapi.util.text.HtmlChunk
import com.intellij.psi.PsiElement
import com.intellij.util.xmlb.annotations.OptionTag
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.*
import com.jetbrains.php.lang.psi.elements.Function
import com.jetbrains.php.lang.psi.resolve.types.PhpType
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.extensions.psi.getTypes
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService

class NullableArgumentPassedInspection : PhpInspection() {
    @OptionTag
    var includeMixedType: Boolean = false

    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object : PhpElementVisitor() {
        override fun visitPhpFunctionCall(element: FunctionReference) = visitCall(element, problemsHolder)

        override fun visitPhpMethodReference(element: MethodReference) = visitCall(element, problemsHolder)

        private fun visitCall(element: FunctionReference, problemsHolder: ProblemsHolder) {
            val resolvedFunction = element.resolve() as? Function ?: return
            val formalParameters = resolvedFunction.parameters

            for ((index, argument) in element.parameters.withIndex()) {
                val formalParam = when {
                    index < formalParameters.size -> formalParameters[index]
                    formalParameters.lastOrNull()?.isVariadic == true -> formalParameters.last()
                    else -> continue
                }

                checkArgument(problemsHolder, argument, formalParam)
            }
        }

        private fun checkArgument(
            problemsHolder: ProblemsHolder,
            argument: PsiElement,
            formalParam: Parameter
        ) {
            val argumentIsNullable = when (argument) {
                is PhpTypedElement -> {
                    val types = argument.getTypes()

                    if (types.isEmpty()) return

                    types.any { it == PhpType._NULL } || (includeMixedType && types.any { it == PhpType._MIXED })
                }

                else -> argument.text.equals("null", ignoreCase = true)
            }

            if (!argumentIsNullable) return

            val declaredType = formalParam.declaredType

            if (declaredType.isEmpty || declaredType.isNullable) return
            if (declaredType.types.contains(PhpType._MIXED)) return
            if (declaredType.types.contains(PhpType._NULL)) return

            ProblemsHolderService.instance.registerProblem(
                problemsHolder, argument,
                "nullable value passed to non-nullable parameter $${formalParam.name}"
            )
        }
    }

    override fun getOptionsPane(): OptPane {
        return OptPane.pane(
            OptCheckbox(
                "includeMixedType",
                PlainMessage("Include mixed type"),
                emptyList(),
                HtmlChunk.raw(
                    "This option includes variables typed <code>mixed</code> as nullable, " +
                            "since <code>mixed</code> can contain <code>null</code>."
                )
            )
        )
    }
}
