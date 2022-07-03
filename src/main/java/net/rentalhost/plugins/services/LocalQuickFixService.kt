package net.rentalhost.plugins.services

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.SmartPsiElementPointer
import com.jetbrains.php.lang.psi.elements.PhpTypeDeclaration
import com.jetbrains.php.lang.psi.elements.impl.PhpFieldTypeImpl
import com.jetbrains.php.lang.psi.elements.impl.PhpParameterTypeImpl
import com.jetbrains.php.lang.psi.elements.impl.PhpReturnTypeImpl

object LocalQuickFixService {
    fun replaceType(
        project: Project,
        element: PsiElement,
        typeReplacement: String
    ) {
        var elementReplacement: PhpTypeDeclaration? = null

        when (element) {
            is PhpReturnTypeImpl -> elementReplacement = FactoryService.createReturnType(project, typeReplacement)
            is PhpParameterTypeImpl -> elementReplacement = FactoryService.createParameterType(project, typeReplacement)
            is PhpFieldTypeImpl -> elementReplacement = FactoryService.createFieldType(project, typeReplacement)
        }

        if (elementReplacement == null) {
            return
        }

        element.replace(elementReplacement)
    }

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
        override fun applyFix(
            project: Project,
            descriptor: ProblemDescriptor
        ) {
            replaceType(
                project,
                if (considerParent) descriptor.psiElement.parent else descriptor.psiElement,
                entireTypesReplacement
            )
        }
    }

    class SimpleDeleteQuickFix constructor(
        quickFixTitle: String
    ): SimpleQuickFix(quickFixTitle) {
        override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
            descriptor.psiElement.delete()
        }
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
