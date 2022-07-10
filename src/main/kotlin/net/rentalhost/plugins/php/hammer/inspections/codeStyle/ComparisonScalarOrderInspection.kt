package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.impl.BinaryExpressionImpl
import net.rentalhost.plugins.extensions.psi.isScalar
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.OptionsPanelService
import net.rentalhost.plugins.services.ProblemsHolderService
import net.rentalhost.plugins.services.TypeService
import javax.swing.JComponent

class ComparisonScalarOrderInspection: PhpInspection() {
    var optionScalarLeft: Boolean = false
    var optionScalarRight: Boolean = true

    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object: PsiElementVisitor() {
        override fun visitElement(element: PsiElement) {
            if (element is BinaryExpressionImpl &&
                TypeService.compareOperations.contains(element.operationType)) {
                val elementLeft = element.leftOperand
                val elementRight = element.rightOperand

                val leftScalar = elementLeft.isScalar()
                val rightScalar = elementRight.isScalar()

                if (optionScalarLeft) {
                    if (leftScalar || !rightScalar)
                        return
                }
                else if (rightScalar || !leftScalar)
                    return

                ProblemsHolderService.registerProblem(
                    problemsHolder,
                    element,
                    if (optionScalarLeft) "Scalar type must be on the left side."
                    else "Scalar type must be on the right side.",
                    LocalQuickFixService.SimpleInlineQuickFix("Flip comparison") {
                        if (elementLeft != null &&
                            elementRight != null) {
                            val elementLeftCopy = elementLeft.copy()

                            elementLeft.replace(elementRight)
                            elementRight.replace(elementLeftCopy)
                        }
                    }
                )
            }
        }
    }

    fun useScalarRight(mode: Boolean) {
        optionScalarLeft = !mode
        optionScalarRight = mode
    }

    override fun createOptionsPanel(): JComponent {
        return OptionsPanelService.create { component: OptionsPanelService ->
            component.delegateRadioCreation { radioComponent: OptionsPanelService.RadioComponent ->
                radioComponent.addOption("Scalar at left", optionScalarLeft) { isSelected: Boolean -> useScalarRight(!isSelected) }
                radioComponent.addOption("Scalar at right", optionScalarRight) { isSelected: Boolean -> useScalarRight(isSelected) }
            }
        }
    }
}
