package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.xmlb.annotations.OptionTag
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.impl.ClassReferenceImpl
import com.jetbrains.php.lang.psi.elements.impl.MemberReferenceImpl
import com.jetbrains.php.lang.psi.elements.impl.PhpClassImpl
import net.rentalhost.plugins.services.FactoryService
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.OptionsPanelService
import net.rentalhost.plugins.services.ProblemsHolderService
import javax.swing.JComponent

enum class OptionReferenceFormat { SELF, NAMED }

class ClassSelfReferenceFormatInspection: PhpInspection() {
    @OptionTag
    var optionReferenceFormat: OptionReferenceFormat = OptionReferenceFormat.SELF

    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object: PsiElementVisitor() {
        override fun visitElement(element: PsiElement) {
            if (element is ClassReferenceImpl &&
                element.parent is MemberReferenceImpl) {
                val elementClass = PsiTreeUtil.getParentOfType(element, PhpClassImpl::class.java) ?: return
                val elementClassName = elementClass.name

                val referenceName = element.text.lowercase()

                if (optionReferenceFormat == OptionReferenceFormat.SELF) {
                    if (referenceName == "self" ||
                        referenceName != elementClassName.lowercase()) {
                        return
                    }
                }
                else if (referenceName != "self" ||
                         referenceName == elementClassName.lowercase()) {
                    return
                }

                val expectedFormat =
                    if (optionReferenceFormat == OptionReferenceFormat.SELF) "self"
                    else elementClassName

                ProblemsHolderService.registerProblem(
                    problemsHolder,
                    element,
                    "Class reference format must be \"$expectedFormat\".",
                    LocalQuickFixService.SimpleReplaceQuickFix(
                        "Replace with \"$expectedFormat\"",
                        FactoryService.createClassReference(problemsHolder.project, expectedFormat)
                    )
                )
            }
        }
    }

    override fun createOptionsPanel(): JComponent {
        return OptionsPanelService.create { component: OptionsPanelService ->
            component.delegateRadioCreation { radioComponent: OptionsPanelService.RadioComponent ->
                radioComponent.addOption("Prefer self reference", optionReferenceFormat === OptionReferenceFormat.SELF) {
                    optionReferenceFormat = OptionReferenceFormat.SELF
                }
                radioComponent.addOption("Prefer ClassName reference", optionReferenceFormat === OptionReferenceFormat.NAMED) {
                    optionReferenceFormat = OptionReferenceFormat.NAMED
                }
            }
        }
    }
}
