package net.rentalhost.plugins.php.hammer.inspections.flowOptimization

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.createSmartPointer
import com.jetbrains.php.config.PhpLanguageLevel
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.FunctionReference
import com.jetbrains.php.lang.psi.elements.impl.*
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.extensions.psi.functionBody
import net.rentalhost.plugins.php.hammer.extensions.psi.isName
import net.rentalhost.plugins.php.hammer.extensions.psi.isShortFunction
import net.rentalhost.plugins.php.hammer.extensions.psi.isVariadicPreceded
import net.rentalhost.plugins.php.hammer.services.FactoryService
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService

class ArrayMapFirstClassInspection : PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object : PhpElementVisitor() {
        override fun visitPhpFunctionCall(element: FunctionReference) {
            if (!element.isName("\\array_map") ||
                element.parameters.size != 2
            )
                return

            val parameterFirst = element.parameters[0] as? PhpExpressionImpl ?: return
            val parameterFunction = parameterFirst.firstPsiChild as? FunctionImpl ?: return

            if (parameterFunction.parameters.size != 1)
                return

            val functionReturnCall =
                if (parameterFunction.isShortFunction()) parameterFunction.lastChild
                else (parameterFunction.functionBody()?.firstPsiChild as? PhpReturnImpl ?: return).argument

            if (functionReturnCall !is FunctionReferenceImpl ||
                functionReturnCall.parameters.size != 1
            )
                return

            val functionReturnCallVariable = (functionReturnCall.parameters[0] as? VariableImpl ?: return)
            val parameterFunctionVariable = parameterFunction.parameters[0]

            if (functionReturnCallVariable.name != parameterFunctionVariable.name ||
                functionReturnCallVariable.isVariadicPreceded()
            )
                return

            ProblemsHolderService.instance.registerProblem(
                problemsHolder,
                parameterFirst,
                "call to array_map() can be replaced by first-class callback",
                QuickFixService.instance.simpleReplace(
                    "Replace with first-class callable",
                    FactoryService.createFunctionCallable(problemsHolder.project, functionReturnCall.name ?: return).createSmartPointer()
                )
            )
        }
    }

    override fun getMinimumSupportedLanguageLevel(): PhpLanguageLevel = PhpLanguageLevel.PHP810
}
