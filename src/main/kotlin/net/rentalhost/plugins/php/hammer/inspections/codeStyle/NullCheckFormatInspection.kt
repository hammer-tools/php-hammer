package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.refactoring.suggested.createSmartPointer
import com.intellij.util.xmlb.annotations.OptionTag
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.lexer.PhpTokenTypes
import com.jetbrains.php.lang.psi.elements.AssignmentExpression
import com.jetbrains.php.lang.psi.elements.BinaryExpression
import com.jetbrains.php.lang.psi.elements.FunctionReference
import com.jetbrains.php.lang.psi.elements.UnaryExpression
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.extensions.psi.isName
import net.rentalhost.plugins.php.hammer.extensions.psi.unparenthesize
import net.rentalhost.plugins.php.hammer.extensions.psi.withOptionalNotOperator
import net.rentalhost.plugins.php.hammer.inspections.enums.OptionNullCheckFormat
import net.rentalhost.plugins.php.hammer.services.FactoryService
import net.rentalhost.plugins.php.hammer.services.OptionsPanelService
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService
import javax.swing.JComponent

class NullCheckFormatInspection : PhpInspection() {
  @OptionTag
  var nullCheckFormat: OptionNullCheckFormat = OptionNullCheckFormat.COMPARISON

  override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object : PhpElementVisitor() {
    override fun visitPhpFunctionCall(function: FunctionReference) {
      if (nullCheckFormat != OptionNullCheckFormat.COMPARISON ||
        !function.isName("is_null") ||
        function.parameters.size != 1)
        return

      val functionBase = function.withOptionalNotOperator()

      val functionParameter = function.parameters[0]
      val functionParameterText =
        if (functionParameter is AssignmentExpression) "(${functionParameter.text})"
        else functionParameter.text

      val functionBaseNot = functionBase is UnaryExpression

      ProblemsHolderService.instance.registerProblem(
        problemsHolder,
        functionBase,
        "null check must be via comparison",
        QuickFixService.instance.simpleReplace(
          "Replace with comparison",
          FactoryService.createComparisonExpression(
            problemsHolder.project,
            functionParameterText,
            if (functionBaseNot) "!==" else "===",
            "null"
          ).createSmartPointer()
        )
      )
    }

    override fun visitPhpBinaryExpression(comparison: BinaryExpression) {
      if (nullCheckFormat != OptionNullCheckFormat.FUNCTION)
        return

      val elementOperation = comparison.operationType
      val comparisonIdentical = elementOperation === PhpTokenTypes.opIDENTICAL
      val comparisonNotIdentical = elementOperation === PhpTokenTypes.opNOT_IDENTICAL

      if (!comparisonIdentical && !comparisonNotIdentical)
        return

      val comparisonLeftOperand = comparison.leftOperand ?: return
      val comparisonRightOperand = comparison.rightOperand ?: return

      val nullLeft = comparisonLeftOperand.text.lowercase() == "null"
      val nullRight = comparisonRightOperand.text.lowercase() == "null"

      if (!nullLeft && !nullRight)
        return

      val functionParameter = FactoryService.createExpression(
        problemsHolder.project,
        if (nullLeft) comparisonRightOperand.text
        else comparisonLeftOperand.text
      )

      ProblemsHolderService.instance.registerProblem(
        problemsHolder,
        comparison,
        "null check must be via is_null() function",
        QuickFixService.instance.simpleReplace(
          "Replace with is_null()",
          FactoryService.createFunctionCall(
            problemsHolder.project, comparisonNotIdentical, "is_null",
            listOf((functionParameter.unparenthesize() ?: return).text)
          ).createSmartPointer()
        )
      )
    }
  }

  override fun createOptionsPanel(): JComponent {
    return OptionsPanelService.create { component: OptionsPanelService ->
      component.delegateRadioCreation { radioComponent: OptionsPanelService.RadioComponent ->
        radioComponent.addOption(
          "Prefer is_null() function", nullCheckFormat === OptionNullCheckFormat.FUNCTION,
          "Your code will look like:: <code>is_null(\$example)</code>"
        ) { nullCheckFormat = OptionNullCheckFormat.FUNCTION }

        radioComponent.addOption(
          "Prefer === comparison", nullCheckFormat === OptionNullCheckFormat.COMPARISON,
          "Your code will look like:: <code>\$example === null</code>"
        ) { nullCheckFormat = OptionNullCheckFormat.COMPARISON }
      }
    }
  }
}
