package net.rentalhost.plugins.php.hammer.inspections.flowOptimization

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.jetbrains.php.config.PhpLanguageLevel
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.impl.*
import net.rentalhost.plugins.extensions.psi.functionBody
import net.rentalhost.plugins.extensions.psi.isShortFunction
import net.rentalhost.plugins.extensions.psi.isVariadicPreceded
import net.rentalhost.plugins.services.ProblemsHolderService

class ArrayMapFirstClassInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object: PsiElementVisitor() {
        override fun visitElement(element: PsiElement) {
            if (element is FunctionReferenceImpl &&
                (element.name ?: return).lowercase() == "array_map" &&
                element.parameters.size == 2) {
                val parameterFirst = element.parameters[0] as? PhpExpressionImpl ?: return
                val parameterFunction = parameterFirst.firstPsiChild as? FunctionImpl ?: return

                if (parameterFunction.parameters.size != 1)
                    return

                val functionReturnCall =
                    if (parameterFunction.isShortFunction()) parameterFunction.lastChild
                    else (parameterFunction.functionBody()?.firstPsiChild as? PhpReturnImpl ?: return).argument

                if (functionReturnCall !is FunctionReferenceImpl ||
                    functionReturnCall.parameters.size != 1)
                    return

                val functionReturnCallVariable = (functionReturnCall.parameters[0] as? VariableImpl ?: return)
                val parameterFunctionVariable = parameterFunction.parameters[0]

                if (functionReturnCallVariable.name != parameterFunctionVariable.name ||
                    functionReturnCallVariable.isVariadicPreceded())
                    return

                ProblemsHolderService.registerProblem(
                    problemsHolder,
                    element,
                    "Call to array_map() can be replaced by first-class callback.",
                )
            }
        }
    }

    override fun getMinimumSupportedLanguageLevel(): PhpLanguageLevel = PhpLanguageLevel.PHP810
}
