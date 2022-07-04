package net.rentalhost.plugins.services

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.SmartPsiElementPointer
import com.jetbrains.php.lang.psi.elements.PhpTypeDeclaration

object LocalQuickFixService {
    abstract class SimpleQuickFix constructor(
        private val quickFixTitle: String
    ): LocalQuickFix {
        override fun getFamilyName(): String = quickFixTitle
    }

    class SimpleTypeReplaceQuickFix(
        quickFixTitle: String,
        private val entireTypesReplacement: String,
        private val considerParent: Boolean = false
    ): SimpleQuickFix(quickFixTitle) {
        override fun applyFix(project: Project, descriptor: ProblemDescriptor): Unit = TypeService.replaceWith(
            project,
            (if (considerParent) descriptor.psiElement.parent else descriptor.psiElement) as PhpTypeDeclaration,
            entireTypesReplacement
        )
    }

    class SimpleDeleteQuickFix constructor(
        quickFixTitle: String
    ): SimpleQuickFix(quickFixTitle) {
        override fun applyFix(project: Project, descriptor: ProblemDescriptor): Unit =
            descriptor.psiElement.delete()
    }

    class SimpleLeafReplaceQuickFix(
        quickFixTitle: String,
        private val leafReplacement: SmartPsiElementPointer<PsiElement>
    ): SimpleQuickFix(quickFixTitle) {
        override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
            descriptor.psiElement.replace(leafReplacement.element!!)
        }
    }
}
