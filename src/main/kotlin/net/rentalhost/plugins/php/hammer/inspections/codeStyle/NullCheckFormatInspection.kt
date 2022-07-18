package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import com.intellij.util.xmlb.annotations.OptionTag
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.lexer.PhpTokenTypes
import com.jetbrains.php.lang.psi.elements.AssignmentExpression
import com.jetbrains.php.lang.psi.elements.BinaryExpression
import com.jetbrains.php.lang.psi.elements.FunctionReference
import com.jetbrains.php.lang.psi.elements.UnaryExpression
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.enums.OptionNullCheckFormat
import net.rentalhost.plugins.extensions.psi.isName
import net.rentalhost.plugins.extensions.psi.unparenthesize
import net.rentalhost.plugins.extensions.psi.withOptionalNotOperator
import net.rentalhost.plugins.services.FactoryService
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.OptionsPanelService
import net.rentalhost.plugins.services.ProblemsHolderService
import javax.swing.JComponent

class NullCheckFormatInspection: PhpInspection() {
    @OptionTag
    var nullCheckFormat: OptionNullCheckFormat = OptionNullCheckFormat.COMPARISON

    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object: PhpElementVisitor() {
        override fun visitPhpFunctionCall(function: FunctionReference) {
            if (nullCheckFormat != OptionNullCheckFormat.COMPARISON)
                return

            if (!function.isName("is_null") ||
                function.parameters.size != 1)
                return

            val functionBase = function.withOptionalNotOperator()

            ProblemsHolderService.registerProblem(
                problemsHolder,
                functionBase,
                "Null check must be via comparison.",
                LocalQuickFixService.SimpleReplaceQuickFix(
                    "Replace with comparison",
                    lazy {
                        val functionParameter = function.parameters[0]
                        val functionParameterText =
                            if (functionParameter is AssignmentExpression) "(${functionParameter.text})"
                            else functionParameter.text

                        val functionBaseNot = functionBase is UnaryExpression

                        FactoryService.createComparisonExpression(
                            problemsHolder.project,
                            functionParameterText,
                            if (functionBaseNot) "!==" else "===",
                            "null"
                        )
                    }
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

            ProblemsHolderService.registerProblem(
                problemsHolder,
                comparison,
                "Null check must be via is_null() function.",
                LocalQuickFixService.SimpleReplaceQuickFix(
                    "Replace with is_null()",
                    FactoryService.createFunctionCall(
                        problemsHolder.project, comparisonNotIdentical, "is_null",
                        listOf((functionParameter.unparenthesize() ?: return).text)
                    )
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
