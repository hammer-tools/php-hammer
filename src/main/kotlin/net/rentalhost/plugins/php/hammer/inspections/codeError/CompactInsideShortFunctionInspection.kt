package net.rentalhost.plugins.php.hammer.inspections.codeError

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.refactoring.suggested.createSmartPointer
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.ArrayHashElement
import com.jetbrains.php.lang.psi.elements.Function
import com.jetbrains.php.lang.psi.elements.FunctionReference
import com.jetbrains.php.lang.psi.elements.impl.StringLiteralExpressionImpl
import com.jetbrains.php.lang.psi.elements.impl.VariableImpl
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.extensions.psi.isName
import net.rentalhost.plugins.php.hammer.extensions.psi.isShortFunction
import net.rentalhost.plugins.php.hammer.services.FactoryService
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService

class CompactInsideShortFunctionInspection : PhpInspection() {
  override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object : PhpElementVisitor() {
    override fun visitPhpFunctionCall(element: FunctionReference) {
      if (!element.isName("\\compact"))
        return

      val elementScopeFunction = PsiTreeUtil.getParentOfType(element, Function::class.java) ?: return

      if (!elementScopeFunction.isShortFunction())
        return

      val allowedNames = elementScopeFunction.parameters.map { parameter -> parameter.name }

      element.parameters.forEach { parameter ->
        if (parameter !is StringLiteralExpressionImpl ||
          !allowedNames.contains(parameter.contents)) {
          val arrayElements = mutableListOf<ArrayHashElement>()

          element.parameters.forEach rebuild@{ eParameter ->
            arrayElements.add(
              when (eParameter) {
                is StringLiteralExpressionImpl -> FactoryService.createArrayKeyValue(problemsHolder.project, eParameter.text, "\$${eParameter.contents}")
                is VariableImpl -> FactoryService.createArrayKeyValue(problemsHolder.project, "'${eParameter.name}'", eParameter.text)

                else -> {
                  arrayElements.clear()

                  return@rebuild
                }
              }
            )
          }

          val problemQuickFix = if (arrayElements.isNotEmpty())
            QuickFixService.instance.simpleReplace(
              "Replace with array",
              FactoryService.createArrayCreationExpression(problemsHolder.project, arrayElements).createSmartPointer()
            )
          else null

          ProblemsHolderService.instance.registerProblem(
            problemsHolder,
            element,
            "usage of compact() inside a short function",
            problemQuickFix
          )

          return
        }
      }
    }
  }
}
