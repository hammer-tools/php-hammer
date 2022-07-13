package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.elementType
import com.intellij.util.xmlb.annotations.OptionTag
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.lexer.PhpTokenTypes
import com.jetbrains.php.lang.psi.elements.impl.ForImpl
import com.jetbrains.php.lang.psi.elements.impl.StatementImpl
import com.jetbrains.php.lang.psi.elements.impl.UnaryExpressionImpl
import net.rentalhost.plugins.enums.OptionUnaryOperatorSideFormat
import net.rentalhost.plugins.services.FactoryService
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.OptionsPanelService
import net.rentalhost.plugins.services.ProblemsHolderService
import javax.swing.JComponent

class UnaryOperatorFormatInspection: PhpInspection() {
    @OptionTag
    var includeForRepeatedExpressions: Boolean = true

    @OptionTag
    var unaryOperatorSide: OptionUnaryOperatorSideFormat = OptionUnaryOperatorSideFormat.RIGHT

    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object: PsiElementVisitor() {
        override fun visitElement(element: PsiElement) {
            if (element is UnaryExpressionImpl) {
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
                else if (elementParent::class !== StatementImpl::class) {
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

                ProblemsHolderService.registerProblem(
                    problemsHolder,
                    element,
                    if (unaryOperationPreferRight) "Unary expression must be written as ${unaryElement.text}${unaryOperator.text}"
                    else "Unary expression must be written as ${unaryOperator.text}${unaryElement.text}",
                    LocalQuickFixService.SimpleReplaceQuickFix(
                        "Swap unary operation elements",
                        if (unaryOperationPreferRight) FactoryService.createUnaryRightOperation(
                            problemsHolder.project,
                            unaryElement.text,
                            unaryOperator.text
                        )
                        else FactoryService.createUnaryLeftOperation(
                            problemsHolder.project,
                            unaryElement.text,
                            unaryOperator.text
                        )
                    )
                )
            }
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
