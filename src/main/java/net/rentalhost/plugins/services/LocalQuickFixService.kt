package net.rentalhost.plugins.services

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.jetbrains.php.lang.psi.PhpPsiElementFactory
import com.jetbrains.php.lang.psi.elements.PhpTypeDeclaration
import com.jetbrains.php.lang.psi.elements.impl.*
import java.util.*

object LocalQuickFixService {
    fun replaceType(
        project: Project,
        element: PsiElement,
        typeReplacement: String
    ) {
        var elementReplacement: PhpTypeDeclaration? = null

        when (element) {
            is PhpReturnTypeImpl -> {
                elementReplacement = PhpPsiElementFactory
                    .createPhpPsiFromText(project, FunctionImpl::class.java, "function dummy(): $typeReplacement {}")
                    .returnType
            }

            is PhpParameterTypeImpl -> {
                elementReplacement = PhpPsiElementFactory
                    .createPhpPsiFromText(project, FunctionImpl::class.java, "function dummy($typeReplacement \$dummy) {}")
                    .getParameter(0)
                    ?.typeDeclaration
            }

            is PhpFieldTypeImpl -> {
                val field = Arrays.stream(
                    PhpPsiElementFactory
                        .createPhpPsiFromText(project, PhpClassImpl::class.java, "class Dummy { public $typeReplacement \$dummy; }")
                        .ownFields
                ).findFirst()

                if (field.isEmpty)
                    return

                elementReplacement = field.get().typeDeclaration
            }
        }

        if (elementReplacement == null) {
            return
        }

        element.replace(elementReplacement)
    }

    class SimpleTypeReplaceQuickFix constructor(
        private val entireTypesReplacement: String,
        private val quickFixTitle: String,
        private val considerParent: Boolean = false
    ) : LocalQuickFix {
        override fun getFamilyName(): String {
            return quickFixTitle
        }

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
}
