package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.createSmartPointer
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.xmlb.annotations.OptionTag
import com.jetbrains.php.config.PhpLanguageLevel
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.ArrayCreationExpression
import com.jetbrains.php.lang.psi.elements.Function
import com.jetbrains.php.lang.psi.elements.MultiassignmentExpression
import com.jetbrains.php.lang.psi.elements.impl.ArrayHashElementImpl
import com.jetbrains.php.lang.psi.elements.impl.StringLiteralExpressionImpl
import com.jetbrains.php.lang.psi.elements.impl.VariableImpl
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.extensions.psi.isRef
import net.rentalhost.plugins.php.hammer.extensions.psi.isShortFunction
import net.rentalhost.plugins.php.hammer.extensions.psi.unpackValues
import net.rentalhost.plugins.php.hammer.inspections.enums.OptionCompactArgumentsFormat
import net.rentalhost.plugins.php.hammer.services.*
import javax.swing.JComponent

class CompactReplacementInspection : PhpInspection() {
    @OptionTag
    var compactArgumentsFormat: OptionCompactArgumentsFormat = OptionCompactArgumentsFormat.POSITIONAL

    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object : PhpElementVisitor() {
        override fun visitPhpArrayCreationExpression(element: ArrayCreationExpression) {
            if (element.parent is MultiassignmentExpression)
                return

            // Inspection must not works when inside of a arrow function due to PHP bug #78970.
            with(PsiTreeUtil.getParentOfType(element, Function::class.java)) {
                if (this != null && this.isShortFunction())
                    return
            }

            val arrayElements = element.unpackValues()

            if (arrayElements.isEmpty())
                return

            val arrayVariables = mutableListOf<String>()

            for (arrayElement in arrayElements) {
                if (arrayElement !is ArrayHashElementImpl)
                    return

                val arrayElementValue = arrayElement.value as? VariableImpl ?: return

                if (arrayElementValue.isRef())
                    return

                val arrayElementKey = arrayElement.key as? StringLiteralExpressionImpl ?: return

                if (arrayElementKey.contents != arrayElementValue.name)
                    return

                arrayVariables.add("'${arrayElementValue.name}'")
            }

            val localCompactArgumentsFormat =
                if (LanguageService.atLeast(problemsHolder.project, PhpLanguageLevel.PHP800)) compactArgumentsFormat
                else OptionCompactArgumentsFormat.POSITIONAL

            ProblemsHolderService.instance.registerProblem(
                problemsHolder,
                element,
                "array can be replaced by compact()",
                QuickFixService.instance.simpleReplace(
                    "Replace with compact()",
                    if (localCompactArgumentsFormat === OptionCompactArgumentsFormat.POSITIONAL)
                        FactoryService.createFunctionCall(problemsHolder.project, "compact", arrayVariables).createSmartPointer()
                    else
                        FactoryService.createFunctionCallNamed(
                            problemsHolder.project, "compact", mapOf(
                                Pair(
                                    "var_name", "[${
                                        arrayVariables.joinToString(",")
                                    }]"
                                )
                            )
                        ).createSmartPointer()
                )
            )
        }
    }

    override fun createOptionsPanel(): JComponent {
        return OptionsPanelService.create { component: OptionsPanelService ->
            component.delegateRadioCreation { radioComponent: OptionsPanelService.RadioComponent ->
                radioComponent.addOption(
                    "Prefer positional arguments", compactArgumentsFormat === OptionCompactArgumentsFormat.POSITIONAL,
                    "The fix will suggest positional arguments (e.g., <code>compact(\"a\", \"b\", \"c\")</code>). " +
                            "For lower PHP versions, this option will be automatically used."
                ) { compactArgumentsFormat = OptionCompactArgumentsFormat.POSITIONAL }

                radioComponent.addOption(
                    "Prefer named arguments", compactArgumentsFormat === OptionCompactArgumentsFormat.NAMED,
                    "The fix will suggest named arguments (e.g., <code>compact(var_name: [\"a\", \"b\", \"c\"])</code>). " +
                            "For PHP 8.0+ only. For lower PHP versions, positional option will be automatically used."
                ) { compactArgumentsFormat = OptionCompactArgumentsFormat.NAMED }
            }
        }
    }
}



