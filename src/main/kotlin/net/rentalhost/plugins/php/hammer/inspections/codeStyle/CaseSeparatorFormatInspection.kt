package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.SmartPointerManager
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType
import com.intellij.util.xmlb.annotations.OptionTag
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.impl.GroupStatementSimpleImpl
import com.jetbrains.php.lang.psi.elements.impl.PhpCaseImpl
import net.rentalhost.plugins.enums.OptionCaseSeparatorFormat
import net.rentalhost.plugins.services.FactoryService
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.OptionsPanelService
import net.rentalhost.plugins.services.ProblemsHolderService
import javax.swing.JComponent

class CaseSeparatorFormatInspection: PhpInspection() {
    @OptionTag
    var optionCaseSeparatorFormat: OptionCaseSeparatorFormat = OptionCaseSeparatorFormat.COLON

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

                    if (elementSeparatorColon && optionCaseSeparatorFormat === OptionCaseSeparatorFormat.SEMICOLON) {
                        elementSeparatorReplacement = FactoryService.createSemicolon(problemsHolder.project)
                    }
                    else if (!elementSeparatorColon && optionCaseSeparatorFormat === OptionCaseSeparatorFormat.COLON) {
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

    override fun createOptionsPanel(): JComponent {
        return OptionsPanelService.create { component: OptionsPanelService ->
            component.delegateRadioCreation { radioComponent: OptionsPanelService.RadioComponent ->
                radioComponent.addOption(
                    "Prefer colon as separator", optionCaseSeparatorFormat === OptionCaseSeparatorFormat.COLON,
                    "So your code will look like: <code>case 'example':</code>"
                ) { optionCaseSeparatorFormat = OptionCaseSeparatorFormat.COLON }

                radioComponent.addOption(
                    "Prefer semicolon as separator", optionCaseSeparatorFormat === OptionCaseSeparatorFormat.SEMICOLON,
                    "So your code will look like: <code>case 'example';</code>"
                ) { optionCaseSeparatorFormat = OptionCaseSeparatorFormat.SEMICOLON }
            }
        }
    }
}
