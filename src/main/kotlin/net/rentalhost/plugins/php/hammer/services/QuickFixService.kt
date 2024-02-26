package net.rentalhost.plugins.php.hammer.services

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Iconable
import com.intellij.psi.PsiElement
import com.intellij.psi.SmartPsiElementPointer
import com.jetbrains.php.lang.psi.elements.PhpTypeDeclaration
import net.rentalhost.plugins.php.hammer.extensions.psi.replaceWith
import net.rentalhost.plugins.php.hammer.interfaces.QuickIntentionAction
import javax.swing.Icon

class QuickFixService(private val projectService: ProjectService) {
    companion object {
        val instance: QuickFixService = QuickFixService(ProjectService.instance)
    }

    abstract class SimpleQuickFix(private val quickFixTitle: String) : LocalQuickFix {
        override fun getFamilyName(): String = "\uD83D\uDD28 $quickFixTitle"
    }

    fun simpleTypeReplace(
        quickFixTitle: String,
        entireTypesReplacement: String,
        considerParent: Boolean = false
    ) = object : SimpleQuickFix(quickFixTitle) {
        override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
            projectService.settings.increaseFixes()

            val phpTypeDeclaration =
                if (considerParent) descriptor.psiElement.parent
                else descriptor.psiElement

            if (phpTypeDeclaration is PhpTypeDeclaration) {
                phpTypeDeclaration.replaceWith(project, entireTypesReplacement)
            }
        }
    }

    fun simpleLeafReplace(
        quickFixTitle: String,
        leafReplacement: SmartPsiElementPointer<out PsiElement>
    ) = object : SimpleQuickFix(quickFixTitle) {
        override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
            with(leafReplacement.element ?: return) {
                projectService.settings.increaseFixes()

                descriptor.psiElement.replace(this)
            }
        }
    }

    fun simpleDelete(
        quickFixTitle: String,
        element: SmartPsiElementPointer<out PsiElement>? = null
    ) = object : SimpleQuickFix(quickFixTitle) {
        override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
            projectService.settings.increaseFixes()

            (element?.element ?: descriptor.psiElement).delete()
        }
    }

    fun simpleReplace(
        quickFixTitle: String,
        replaceFrom: SmartPsiElementPointer<out PsiElement>? = null,
        replaceTo: SmartPsiElementPointer<out PsiElement>
    ) = object : SimpleQuickFix(quickFixTitle) {
        override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
            with(replaceTo.element ?: return) {
                projectService.settings.increaseFixes()

                (replaceFrom?.element ?: descriptor.psiElement).replace(this)
            }
        }
    }

    fun simpleReplace(quickFixTitle: String, replaceTo: SmartPsiElementPointer<out PsiElement>) =
        simpleReplace(quickFixTitle, null, replaceTo)

    fun simpleInline(
        quickFixTitle: String,
        applyFix: () -> Unit
    ) = object : SimpleQuickFix(quickFixTitle) {
        override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
            projectService.settings.increaseFixes()

            applyFix.invoke()
        }
    }

    abstract class SimpleAction(
        private val intentionTitle: String,
        private val action: () -> Unit
    ) : LocalQuickFix, QuickIntentionAction {
        override fun getName(): String = intentionTitle
        override fun getFamilyName(): String = intentionTitle
        override fun startInWriteAction(): Boolean = false

        override fun applyFix(project: Project, descriptor: ProblemDescriptor) =
            action.invoke()
    }

    fun simpleAction(
        intentionTitle: String,
        icon: Icon,
        action: () -> Unit
    ): SimpleAction =
        object : SimpleAction(intentionTitle, action), Iconable {
            override fun getIcon(ignoredFlags: Int): Icon = icon
        }
}
