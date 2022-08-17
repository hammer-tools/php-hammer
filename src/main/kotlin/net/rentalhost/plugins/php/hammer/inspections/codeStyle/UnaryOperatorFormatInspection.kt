package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.util.elementType
import com.intellij.refactoring.suggested.createSmartPointer
import com.intellij.util.xmlb.annotations.OptionTag
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.lexer.PhpTokenTypes
import com.jetbrains.php.lang.psi.elements.UnaryExpression
import com.jetbrains.php.lang.psi.elements.impl.ForImpl
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.hammer.extensions.psi.isStrictlyStatement
import net.rentalhost.plugins.hammer.services.FactoryService
import net.rentalhost.plugins.hammer.services.OptionsPanelService
import net.rentalhost.plugins.php.hammer.inspections.enums.OptionUnaryOperatorSideFormat
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService
import javax.swing.JComponent

class UnaryOperatorFormatInspection: PhpInspection() {
    @OptionTag
    var includeForRepeatedExpressions: Boolean = true

    @OptionTag
    var unaryOperatorSide: OptionUnaryOperatorSideFormat = OptionUnaryOperatorSideFormat.RIGHT

    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        override fun visitPhpUnaryExpression(element: UnaryExpression) {
            val unaryOperator = element.operation ?: return
            val unaryIncrease = unaryOperator.elementType === PhpTokenTypes.opINCREMENT
            val unaryDecrease = unaryOperator.elementType === PhpTokenTypes.opDECREMENT

            if (!unaryIncrease && !unaryDecrease)
                return

            val unaryElement = element.firstPsiChild ?: return

            val elementParent = element.parent

            if (elementParent is ForImpl) {
                if (!includeForRepeatedExpressions ||
                    !elementParent.repeatedExpressions.contains(element))
                    return
            }
            else if (!elementParent.isStrictlyStatement()) {
                return
            }

            val unaryOperationRight = element.lastChild === unaryOperator
            val unaryOperationPreferRight = unaryOperatorSide == OptionUnaryOperatorSideFormat.RIGHT

            if (unaryOperationPreferRight) {
                if (unaryOperationRight)
                    return
            }
            else if (!unaryOperationRight) {
                return
            }

            ProblemsHolderService.instance.registerProblem(
                problemsHolder,
                element,
                if (unaryOperationPreferRight) "unary expression must be written as ${unaryElement.text}${unaryOperator.text}"
                else "unary expression must be written as ${unaryOperator.text}${unaryElement.text}",
                QuickFixService.instance.simpleReplace(
                    "Swap unary operation elements",
                    if (unaryOperationPreferRight) FactoryService.createUnaryRightOperation(
                        problemsHolder.project,
                        unaryElement.text,
                        unaryOperator.text
                    ).createSmartPointer()
                    else FactoryService.createUnaryLeftOperation(
                        problemsHolder.project,
                        unaryElement.text,
                        unaryOperator.text
                    ).createSmartPointer()
                )
            )
        }
    }

    override fun createOptionsPanel(): JComponent {
        return OptionsPanelService.create { component: OptionsPanelService ->
            component.addCheckbox(
                "Include for() repeated expressions", includeForRepeatedExpressions,
                "This option allows the inspection to act on the third expression of the <code>for(; ; \$a++)</code> looping."
            ) { includeForRepeatedExpressions = it }

            component.addLabel("Preferred format:")

            component.delegateRadioCreation { radioComponent: OptionsPanelService.RadioComponent ->
                radioComponent.addOption(
                    "Prefer left side", unaryOperatorSide === OptionUnaryOperatorSideFormat.LEFT,
                    "Your unary operations will look like: <code>++\$example</code>"
                ) { unaryOperatorSide = OptionUnaryOperatorSideFormat.LEFT }

                radioComponent.addOption(
                    "Prefer right side", unaryOperatorSide === OptionUnaryOperatorSideFormat.RIGHT,
                    "Your unary operations will look like: <code>\$example++</code>"
                ) { unaryOperatorSide = OptionUnaryOperatorSideFormat.RIGHT }
            }
        }
    }
}
