package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.elementType
import com.intellij.util.xmlb.annotations.OptionTag
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.lexer.PhpTokenTypes
import com.jetbrains.php.lang.psi.elements.Statement
import com.jetbrains.php.lang.psi.elements.impl.MemberReferenceImpl
import com.jetbrains.php.lang.psi.elements.impl.UnaryExpressionImpl
import com.jetbrains.php.lang.psi.elements.impl.VariableImpl
import net.rentalhost.plugins.enums.OptionUnaryOperatorSideFormat
import net.rentalhost.plugins.services.OptionsPanelService
import net.rentalhost.plugins.services.ProblemsHolderService
import javax.swing.JComponent

class UnaryOperatorFormatInspection: PhpInspection() {
    @OptionTag
    var optionUnaryOperatorSide: OptionUnaryOperatorSideFormat = OptionUnaryOperatorSideFormat.RIGHT

    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object: PsiElementVisitor() {
        override fun visitElement(element: PsiElement) {
            if (element is UnaryExpressionImpl) {
                if (element.parent !is Statement)
                    return

                val unaryOperator = element.operation ?: return
                val unaryIncrease = unaryOperator.elementType === PhpTokenTypes.opINCREMENT
                val unaryDecrease = unaryOperator.elementType === PhpTokenTypes.opDECREMENT

                if (!unaryIncrease && !unaryDecrease)
                    return

                val unaryOperationRight = element.lastChild === unaryOperator

                if (optionUnaryOperatorSide == OptionUnaryOperatorSideFormat.RIGHT) {
                    if (unaryOperationRight)
                        return
                }
                else if (!unaryOperationRight)
                    return

                val unaryElement = element.firstPsiChild ?: return
                val unaryElementText = when (unaryElement) {
                    is VariableImpl -> unaryElement.text
                    is MemberReferenceImpl -> unaryElement.text
                    else -> "\$example"
                }

                ProblemsHolderService.registerProblem(
                    problemsHolder,
                    element,
                    if (unaryOperationRight) "Unary expression must be written as $unaryElementText${unaryOperator.text}"
                    else "Unary expression must be written as ${unaryOperator.text}$unaryElementText"
                )
            }
        }
    }

    override fun createOptionsPanel(): JComponent {
        return OptionsPanelService.create { component: OptionsPanelService ->
            component.delegateRadioCreation { radioComponent: OptionsPanelService.RadioComponent ->
                radioComponent.addOption(
                    "Prefer left side", optionUnaryOperatorSide === OptionUnaryOperatorSideFormat.LEFT,
                    "Your unary operations will look like: <code>++\$example</code>"
                ) { optionUnaryOperatorSide = OptionUnaryOperatorSideFormat.LEFT }

                radioComponent.addOption(
                    "Prefer right side", optionUnaryOperatorSide === OptionUnaryOperatorSideFormat.RIGHT,
                    "Your unary operations will look like: <code>\$example++</code>"
                ) { optionUnaryOperatorSide = OptionUnaryOperatorSideFormat.RIGHT }
            }
        }
    }
}
