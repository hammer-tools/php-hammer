package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.impl.ArrayCreationExpressionImpl
import com.jetbrains.php.lang.psi.elements.impl.ArrayHashElementImpl
import com.jetbrains.php.lang.psi.elements.impl.PhpPsiElementImpl
import net.rentalhost.plugins.services.ArrayService
import net.rentalhost.plugins.services.ProblemsHolderService
import net.rentalhost.plugins.services.TypeService

class ArrayPackableInspection: PhpInspection() {
    override fun buildVisitor(
        problemsHolder: ProblemsHolder,
        isOnTheFly: Boolean
    ): PsiElementVisitor {
        return object: PsiElementVisitor() {
            override fun visitElement(element: PsiElement) {
                if (element is ArrayCreationExpressionImpl) {
                    val elementChildren = ArrayService.unpackArray(element)

                    if (elementChildren.isEmpty())
                        return

                    var elementContainsIndex = false

                    for ((elementChildIndex, elementChild) in elementChildren.withIndex()) {
                        if (elementChild is ArrayHashElementImpl) {
                            elementContainsIndex = true

                            if (elementChild.key == null ||
                                elementChild.key!!.text != elementChildIndex.toString()) {
                                return
                            }
                        }
                        else if (TypeService.isVariadic(elementChild)) {
                            return
                        }
                    }

                    if (!elementContainsIndex)
                        return

                    ProblemsHolderService.registerProblem(
                        problemsHolder,
                        element,
                        "Packed array can be simplified.",
                        DropArrayKeysQuickFix()
                    )
                }
            }
        }
    }

    class DropArrayKeysQuickFix: LocalQuickFix {
        override fun getFamilyName(): String = "Drop the array keys"

        private fun dropArrayKeys(project: Project, array: PsiElement) {
            for (arrayElement in array.children) {
                if (arrayElement is ArrayHashElementImpl) {
                    val arrayElementNew = ArrayService.createArrayValue(project, arrayElement)

                    if (arrayElementNew != null) {
                        arrayElement.replace(arrayElementNew)
                    }
                }
                else if (TypeService.isVariadic(arrayElement, ArrayCreationExpressionImpl::class.java) &&
                         arrayElement is PhpPsiElementImpl<*>) {
                    dropArrayKeys(project, arrayElement.firstPsiChild!!)
                }
            }
        }

        override fun applyFix(
            project: Project,
            descriptor: ProblemDescriptor
        ) {
            dropArrayKeys(project, descriptor.psiElement)
        }
    }
}
