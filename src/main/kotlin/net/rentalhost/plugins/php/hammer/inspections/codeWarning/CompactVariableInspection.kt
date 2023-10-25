package net.rentalhost.plugins.php.hammer.inspections.codeWarning

import com.intellij.codeInspection.ProblemsHolder
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
import net.rentalhost.plugins.php.hammer.services.OptionsPanelService
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService
import javax.swing.JComponent

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
          .filterIsInstance(Variable::class.java)
          .forEach {
            val variableTypes by lazy { it.getTypes() }

            if (!includeStrings) {
              if (variableTypes.size == 1 &&
                variableTypes[0] === PhpType._STRING) {
                return@forEach
              }
            }

            if (!includeArrays) {
              if (variableTypes.size == 1 &&
                variableTypes[0] === PhpType._ARRAY) {
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

  override fun createOptionsPanel(): JComponent {
    return OptionsPanelService.create { component: OptionsPanelService ->
      component.addCheckbox(
        "Include string-type", includeStrings,
        "This option allows this inspection to consider variables that stores a value of type string, " +
          "which can represent the name of a variable that will be packed by <code>compact()</code>. " +
          "However, how is this not very common this option is enabled by default."
      ) { includeStrings = it }

      component.addCheckbox(
        "Include array-type", includeArrays,
        "This option allows this inspection to consider variables that stores a value of type array, " +
          "which can represent the names of variables that will be packed by <code>compact()</code>. " +
          "However, how is this not very common this option is enabled by default."
      ) { includeArrays = it }
    }
  }
}
