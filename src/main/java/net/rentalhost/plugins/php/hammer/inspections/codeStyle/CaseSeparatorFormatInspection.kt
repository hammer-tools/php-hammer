package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.SmartPointerManager
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.impl.GroupStatementSimpleImpl
import com.jetbrains.php.lang.psi.elements.impl.PhpCaseImpl
import net.rentalhost.plugins.services.*
import javax.swing.JComponent

class CaseSeparatorFormatInspection: PhpInspection() {
    var optionFormatColon: Boolean = true
    var optionFormatSemicolon: Boolean = false

    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object: PsiElementVisitor() {
        override fun visitElement(element: PsiElement) {
            if (element is PhpCaseImpl) {
                val elementSeparator = PsiTreeUtil.skipWhitespacesAndCommentsForward(
                    if (element.condition is GroupStatementSimpleImpl) element.firstChild
                    else element.condition
                )

                if (elementSeparator is LeafPsiElement) {
                    val elementSeparatorColon = elementSeparator.text == ":"
                    var elementSeparatorReplacement: PsiElement? = null

                    if (elementSeparatorColon && optionFormatSemicolon) {
                        elementSeparatorReplacement = FactoryService.createSemicolon(problemsHolder.project)
                    }
                    else if (!elementSeparatorColon && optionFormatColon) {
                        elementSeparatorReplacement = FactoryService.createColon(problemsHolder.project)
                    }

                    if (elementSeparatorReplacement != null) {
                        ProblemsHolderService.registerProblem(
                            problemsHolder,
                            elementSeparator,
                            "Wrong switch() \"${element.firstChild.text}\" separator.",
                            LocalQuickFixService.SimpleLeafReplaceQuickFix(
                                "Replace with ${elementSeparatorReplacement.elementType.toString()} separator",
                                SmartPointerManager.createPointer(elementSeparatorReplacement)
                            )
                        )
                    }
                }
            }
        }
    }

    fun useFormatColon(mode: Boolean) {
        optionFormatColon = mode
        optionFormatSemicolon = !mode
    }

    override fun createOptionsPanel(): JComponent {
        return OptionsPanelService.create { component: OptionsPanelService ->
            component.delegateRadioCreation { radioComponent: OptionsPanelService.RadioComponent ->
                radioComponent.addOption("Use colon", optionFormatColon) { isSelected: Boolean -> useFormatColon(isSelected) }
                radioComponent.addOption("Use semicolon", optionFormatSemicolon) { isSelected: Boolean -> useFormatColon(!isSelected) }
            }
        }
    }
}
