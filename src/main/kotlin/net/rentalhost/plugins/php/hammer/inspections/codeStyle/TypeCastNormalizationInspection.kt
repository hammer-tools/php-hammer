package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.lexer.PhpTokenTypes
import net.rentalhost.plugins.extensions.dropWhitespace
import net.rentalhost.plugins.services.OptionsPanelService
import net.rentalhost.plugins.services.ProblemsHolderService
import javax.swing.JComponent

class TypeCastNormalizationInspection: PhpInspection() {
    var optionFormatShort: Boolean = true
    var optionFormatLong: Boolean = false

    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object: PsiElementVisitor() {
        override fun visitElement(element: PsiElement) {
            if (element is LeafPsiElement) {
                with(element.elementType) {
                    val castTo = when (this) {
                        PhpTokenTypes.opFLOAT_CAST -> "(float)"
                        PhpTokenTypes.opINTEGER_CAST -> if (optionFormatShort) "(int)" else "(integer)"
                        PhpTokenTypes.opBOOLEAN_CAST -> if (optionFormatShort) "(bool)" else "(boolean)"
                        else -> return
                    }

                    if (element.text.dropWhitespace() == castTo)
                        return

                    ProblemsHolderService.registerProblem(
                        problemsHolder,
                        element,
                        "Type cast must be written as $castTo"
                    )
                }
            }
        }
    }

    fun useOptionFormatShort(mode: Boolean) {
        optionFormatShort = mode
        optionFormatLong = !mode
    }

    override fun createOptionsPanel(): JComponent {
        return OptionsPanelService.create { component: OptionsPanelService ->
            component.delegateRadioCreation { radioComponent: OptionsPanelService.RadioComponent ->
                radioComponent.addOption("Use short format (int, bool)", optionFormatShort) { isSelected: Boolean -> useOptionFormatShort(isSelected) }
                radioComponent.addOption("Use long format (integer, boolean)", optionFormatLong) { isSelected: Boolean -> useOptionFormatShort(!isSelected) }
            }
        }
    }
}
