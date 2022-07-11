package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.BinaryExpression
import com.jetbrains.php.lang.psi.elements.Statement
import com.jetbrains.php.lang.psi.elements.impl.FunctionReferenceImpl
import com.jetbrains.php.lang.psi.elements.impl.StringLiteralExpressionImpl
import com.jetbrains.php.lang.psi.elements.impl.VariableImpl
import net.rentalhost.plugins.services.CastService
import net.rentalhost.plugins.services.FactoryService
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.ProblemsHolderService

class CastIntvalInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object: PsiElementVisitor() {
        override fun visitElement(element: PsiElement) {
            if (element is FunctionReferenceImpl) {
                val functionName = (element.name ?: return).lowercase()

                if (functionName == "settype") {
                    return processSetTypeCast(problemsHolder, element)
                }

                val castType = CastService.castFunctions[functionName]

                if (castType != null) {
                    processFunctionCast(problemsHolder, element, castType)
                }
            }
        }
    }

    private fun processSetTypeCast(problemsHolder: ProblemsHolder, element: FunctionReferenceImpl) {
        if (element.parent !is Statement ||
            element.parameters.size < 2)
            return

        val castElement = element.parameters[0] as? VariableImpl ?: return
        val castType = element.parameters[1] as? StringLiteralExpressionImpl ?: return
        val castTypeTo = CastService.castSetType[castType.contents.lowercase()] ?: return

        if (castTypeTo == "null") {
            return ProblemsHolderService.registerProblem(
                problemsHolder,
                element,
                "This function cast can be replaced with null.",
                LocalQuickFixService.SimpleReplaceQuickFix(
                    "Replace with null",
                    element.parent,
                    FactoryService.createAssignmentStatement(problemsHolder.project, "\$${castElement.name} = null;")
                )
            )
        }

        ProblemsHolderService.registerProblem(
            problemsHolder,
            element,
            "This function cast can be replaced with the type cast ($castTypeTo).",
            LocalQuickFixService.SimpleReplaceQuickFix(
                "Replace with type cast ($castTypeTo)",
                element.parent,
                FactoryService.createAssignmentStatement(problemsHolder.project, "\$${castElement.name} = ($castTypeTo) \$${castElement.name};")
            )
        )
    }

    private fun processFunctionCast(problemsHolder: ProblemsHolder, element: FunctionReferenceImpl, castTypeTo: String) {
        when (element.parameters.size) {
            1 -> Unit
            2 -> if (castTypeTo == "int" && element.parameters[1].text == "10") Unit else return
            else -> return
        }

        val expressionElement = element.parameters[0]
        val expressionText =
            if (expressionElement is BinaryExpression) "(${expressionElement.text})"
            else expressionElement.text

        ProblemsHolderService.registerProblem(
            problemsHolder,
            element,
            "This function cast can be replaced with the type cast ($castTypeTo).",
            LocalQuickFixService.SimpleReplaceQuickFix(
                "Replace with type cast ($castTypeTo)",
                FactoryService.createTypeCastExpression(problemsHolder.project, castTypeTo, expressionText)
            )
        )
    }
}
