package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import com.intellij.util.xmlb.annotations.OptionTag
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.lexer.PhpTokenTypes
import com.jetbrains.php.lang.psi.elements.BinaryExpression
import com.jetbrains.php.lang.psi.elements.FunctionReference
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.enums.OptionNullCheckFormat
import net.rentalhost.plugins.extensions.psi.isName
import net.rentalhost.plugins.extensions.psi.withOptionalNotOperator
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

            ProblemsHolderService.registerProblem(
                problemsHolder,
                function.withOptionalNotOperator(),
                "Null check must be via comparison."
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

            val nullLeft = (comparison.leftOperand ?: return).text.lowercase() == "null"
            val nullRight = (comparison.rightOperand ?: return).text.lowercase() == "null"

            if (!nullLeft && !nullRight)
                return

            ProblemsHolderService.registerProblem(
                problemsHolder,
                comparison,
                "Null check must be via is_null() function."
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
