package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.refactoring.suggested.createSmartPointer
import com.intellij.util.xmlb.annotations.OptionTag
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.BinaryExpression
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.hammer.extensions.psi.isScalar
import net.rentalhost.plugins.hammer.extensions.psi.swap
import net.rentalhost.plugins.hammer.services.OptionsPanelService
import net.rentalhost.plugins.hammer.services.TypeService
import net.rentalhost.plugins.php.hammer.inspections.enums.OptionComparisonScalarSide
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService
import javax.swing.JComponent

class ComparisonScalarOrderInspection: PhpInspection() {
    @OptionTag
    var comparisonScalarSide: OptionComparisonScalarSide = OptionComparisonScalarSide.RIGHT

    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        override fun visitPhpBinaryExpression(element: BinaryExpression) {
            if (!TypeService.compareOperations.contains(element.operationType))
                return

            val elementLeft = element.leftOperand
            val elementRight = element.rightOperand

            val leftScalar = elementLeft.isScalar()
            val rightScalar = elementRight.isScalar()

            if (comparisonScalarSide === OptionComparisonScalarSide.LEFT) {
                if (leftScalar || !rightScalar)
                    return
            }
            else if (rightScalar || !leftScalar)
                return

            val elementLeftPointer = elementLeft?.createSmartPointer() ?: return
            val elementRightPointer = elementRight?.createSmartPointer() ?: return

            ProblemsHolderService.instance.registerProblem(
                problemsHolder,
                element,
                if (comparisonScalarSide === OptionComparisonScalarSide.LEFT) "scalar type must be on the left side"
                else "scalar type must be on the right side",
                QuickFixService.instance.simpleInline("Flip comparison") {
                    (elementLeftPointer.element ?: return@simpleInline)
                        .swap(elementRightPointer.element ?: return@simpleInline)
                }
            )
        }
    }

    override fun createOptionsPanel(): JComponent {
        return OptionsPanelService.create { component: OptionsPanelService ->
            component.delegateRadioCreation { radioComponent: OptionsPanelService.RadioComponent ->
                radioComponent.addOption(
                    "Prefer scalar at left", comparisonScalarSide === OptionComparisonScalarSide.LEFT,
                    "Your conditionals will look like: <code>true === \$example</code>"
                ) { comparisonScalarSide = OptionComparisonScalarSide.LEFT }

                radioComponent.addOption(
                    "Prefer scalar at right", comparisonScalarSide === OptionComparisonScalarSide.RIGHT,
                    "Your conditionals will look like: <code>\$example === true</code>"
                ) { comparisonScalarSide = OptionComparisonScalarSide.RIGHT }
            }
        }
    }
}
