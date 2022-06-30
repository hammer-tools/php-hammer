package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.jetbrains.php.config.PhpLanguageLevel
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.PhpTypeDeclaration
import com.jetbrains.php.lang.psi.elements.impl.PhpTypeDeclarationImpl
import com.jetbrains.php.lang.psi.resolve.types.PhpType
import net.rentalhost.plugins.services.LocalQuickFixService.SimpleTypeReplaceQuickFix
import net.rentalhost.plugins.services.OptionsPanelService
import net.rentalhost.plugins.services.OptionsPanelService.RadioComponent
import net.rentalhost.plugins.services.ProblemsHolderService
import net.rentalhost.plugins.services.TypeService
import javax.swing.JComponent

class NullableTypesFormatInspection: PhpInspection() {
    var FORMAT_SHORT = false
    var FORMAT_LONG = true

    override fun buildVisitor(
        problemsHolder: ProblemsHolder,
        isOnTheFly: Boolean
    ): PsiElementVisitor {
        return object: PsiElementVisitor() {
            override fun visitElement(element: PsiElement) {
                if (element is PhpTypeDeclarationImpl) {
                    val elementType = (element as PhpTypeDeclaration).type
                    val elementTypes = elementType.types

                    if (elementTypes.size == 2 &&
                        elementTypes.contains(PhpType._NULL)) {
                        val elementTypeText = element.getText()
                        val elementTypeIsShort = elementTypeText.startsWith("?")
                        var elementTypeReplacementSuggestion: String? = null

                        if (elementTypeIsShort && FORMAT_LONG) {
                            elementTypeReplacementSuggestion = "${elementTypeText.substring(1)}|null"
                        }
                        else if (!elementTypeIsShort && FORMAT_SHORT) {
                            val elementTypeSingular = TypeService.exceptNull(elementTypeText).findFirst()

                            if (elementTypeSingular.isPresent) {
                                elementTypeReplacementSuggestion = "?${elementTypeSingular.get()}"
                            }
                        }

                        if (elementTypeReplacementSuggestion == null)
                            return

                        ProblemsHolderService.registerProblem(
                            problemsHolder,
                            element,
                            "Nullable type must be written as \"$elementTypeReplacementSuggestion\".",
                            SimpleTypeReplaceQuickFix(
                                elementTypeReplacementSuggestion,
                                if (FORMAT_LONG) "Replace with the long format" else "Replace with the short format"
                            )
                        )
                    }
                }
            }
        }
    }

    override fun getMinimumSupportedLanguageLevel(): PhpLanguageLevel {
        return PhpLanguageLevel.PHP800
    }

    override fun createOptionsPanel(): JComponent {
        return OptionsPanelService.create { component: OptionsPanelService ->
            component.delegateRadioCreation { radioComponent: RadioComponent ->
                radioComponent.addOption("Use short format (\"?int\")", FORMAT_SHORT) { isSelected: Boolean -> FORMAT_SHORT = isSelected }
                radioComponent.addOption("Use long format (\"int|null\")", FORMAT_LONG) { isSelected: Boolean -> FORMAT_LONG = isSelected }
            }
        }
    }
}
