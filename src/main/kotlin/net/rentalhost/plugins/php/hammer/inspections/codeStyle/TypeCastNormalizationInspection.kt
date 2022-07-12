package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.util.xmlb.annotations.OptionTag
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.lexer.PhpTokenTypes
import net.rentalhost.plugins.enums.OptionTypeCastNormalizationFormat
import net.rentalhost.plugins.services.FactoryService
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.OptionsPanelService
import net.rentalhost.plugins.services.ProblemsHolderService
import javax.swing.JComponent

class TypeCastNormalizationInspection: PhpInspection() {
    @OptionTag
    var optionTypeCastNormalizationFormat: OptionTypeCastNormalizationFormat = OptionTypeCastNormalizationFormat.SHORT

    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object: PsiElementVisitor() {
        override fun visitElement(element: PsiElement) {
            if (element is LeafPsiElement) {
                with(element.elementType) {
                    val castTo = when (this) {
                        PhpTokenTypes.opFLOAT_CAST -> "(float)"
                        PhpTokenTypes.opINTEGER_CAST -> if (optionTypeCastNormalizationFormat === OptionTypeCastNormalizationFormat.SHORT) "(int)" else "(integer)"
                        PhpTokenTypes.opBOOLEAN_CAST -> if (optionTypeCastNormalizationFormat === OptionTypeCastNormalizationFormat.SHORT) "(bool)" else "(boolean)"
                        else -> return
                    }

                    if (element.text == castTo)
                        return

                    ProblemsHolderService.registerProblem(
                        problemsHolder,
                        element,
                        "Type cast must be written as $castTo",
                        LocalQuickFixService.SimpleReplaceQuickFix(
                            "Replace with $castTo",
                            FactoryService.createTypeCast(problemsHolder.project, castTo)
                        )
                    )
                }
            }
        }
    }

    override fun createOptionsPanel(): JComponent {
        return OptionsPanelService.create { component: OptionsPanelService ->
            component.delegateRadioCreation { radioComponent: OptionsPanelService.RadioComponent ->
                radioComponent.addOption(
                    "Prefer short format", optionTypeCastNormalizationFormat === OptionTypeCastNormalizationFormat.SHORT,
                    "Your casts will look like: <code>(int) \$example</code>"
                ) { optionTypeCastNormalizationFormat = OptionTypeCastNormalizationFormat.SHORT }

                radioComponent.addOption(
                    "Prefer long format", optionTypeCastNormalizationFormat === OptionTypeCastNormalizationFormat.LONG,
                    "Your casts will look like: <code>(integer) \$example</code>"
                ) { optionTypeCastNormalizationFormat = OptionTypeCastNormalizationFormat.LONG }
            }
        }
    }
}
