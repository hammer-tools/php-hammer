package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.util.xmlb.annotations.OptionTag
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.impl.BinaryExpressionImpl
import net.rentalhost.plugins.extensions.psi.isScalar
import net.rentalhost.plugins.extensions.psi.swap
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.OptionsPanelService
import net.rentalhost.plugins.services.ProblemsHolderService
import net.rentalhost.plugins.services.TypeService
import javax.swing.JComponent

enum class OptionScalarSide { LEFT, RIGHT }

class ComparisonScalarOrderInspection: PhpInspection() {
    @OptionTag
    var optionScalarSide: OptionScalarSide = OptionScalarSide.RIGHT

    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object: PsiElementVisitor() {
        override fun visitElement(element: PsiElement) {
            if (element is BinaryExpressionImpl &&
                TypeService.compareOperations.contains(element.operationType)) {
                val elementLeft = element.leftOperand
                val elementRight = element.rightOperand

                val leftScalar = elementLeft.isScalar()
                val rightScalar = elementRight.isScalar()

                if (optionScalarSide === OptionScalarSide.LEFT) {
                    if (leftScalar || !rightScalar)
                        return
                }
                else if (rightScalar || !leftScalar)
                    return

                ProblemsHolderService.registerProblem(
                    problemsHolder,
                    element,
                    if (optionScalarSide === OptionScalarSide.LEFT) "Scalar type must be on the left side."
                    else "Scalar type must be on the right side.",
                    LocalQuickFixService.SimpleInlineQuickFix("Flip comparison") {
                        (elementLeft ?: return@SimpleInlineQuickFix)
                            .swap(elementRight ?: return@SimpleInlineQuickFix)
                    }
                )
            }
        }
    }

    override fun createOptionsPanel(): JComponent {
        return OptionsPanelService.create { component: OptionsPanelService ->
            component.delegateRadioCreation { radioComponent: OptionsPanelService.RadioComponent ->
                radioComponent.addOption("Scalar at left", optionScalarSide === OptionScalarSide.LEFT) { optionScalarSide = OptionScalarSide.LEFT }
                radioComponent.addOption("Scalar at right", optionScalarSide === OptionScalarSide.RIGHT) { optionScalarSide = OptionScalarSide.RIGHT }
            }
        }
    }
}
