package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.refactoring.suggested.createSmartPointer
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.BinaryExpression
import com.jetbrains.php.lang.psi.elements.FunctionReference
import com.jetbrains.php.lang.psi.elements.Statement
import com.jetbrains.php.lang.psi.elements.impl.StringLiteralExpressionImpl
import com.jetbrains.php.lang.psi.elements.impl.VariableImpl
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.services.CastService
import net.rentalhost.plugins.php.hammer.services.FactoryService
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService

class CastIntvalInspection : PhpInspection() {
  override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object : PhpElementVisitor() {
    override fun visitPhpFunctionCall(element: FunctionReference) {
      val functionName = (element.name ?: return).lowercase()

      if (functionName == "settype")
        return processSetTypeCast(problemsHolder, element)

      val castType = CastService.castFunctions[functionName]

      if (castType != null)
        processFunctionCast(problemsHolder, element, castType)
    }
  }

  private fun processSetTypeCast(problemsHolder: ProblemsHolder, element: FunctionReference) {
    if (element.parent !is Statement ||
      element.parameters.size < 2)
      return

    val castElement = element.parameters[0] as? VariableImpl ?: return
    val castType = element.parameters[1] as? StringLiteralExpressionImpl ?: return
    val castTypeTo = CastService.castSetType[castType.contents.lowercase()] ?: return

    if (castTypeTo == "null") {
      return ProblemsHolderService.instance.registerProblem(
        problemsHolder,
        element,
        "function cast can be replaced with null",
        QuickFixService.instance.simpleReplace(
          "Replace with null",
          element.parent.createSmartPointer(),
          FactoryService.createAssignmentStatement(problemsHolder.project, "\$${castElement.name} = null;").createSmartPointer()
        )
      )
    }

    ProblemsHolderService.instance.registerProblem(
      problemsHolder,
      element,
      "function cast can be replaced with the type cast ($castTypeTo)",
      QuickFixService.instance.simpleReplace(
        "Replace with type cast ($castTypeTo)",
        element.parent.createSmartPointer(),
        FactoryService.createAssignmentStatement(problemsHolder.project, "\$${castElement.name} = ($castTypeTo) \$${castElement.name};").createSmartPointer()
      )
    )
  }

  private fun processFunctionCast(problemsHolder: ProblemsHolder, element: FunctionReference, castTypeTo: String) {
    when (element.parameters.size) {
      1 -> Unit
      2 -> if (castTypeTo == "int" && element.parameters[1].text == "10") Unit else return
      else -> return
    }

    val expressionElement = element.parameters[0]
    val expressionText =
      if (expressionElement is BinaryExpression) "(${expressionElement.text})"
      else expressionElement.text

    ProblemsHolderService.instance.registerProblem(
      problemsHolder,
      element,
      "function cast can be replaced with the type cast ($castTypeTo)",
      QuickFixService.instance.simpleReplace(
        "Replace with type cast ($castTypeTo)",
        FactoryService.createTypeCastExpression(problemsHolder.project, castTypeTo, expressionText).createSmartPointer()
      )
    )
  }
}
