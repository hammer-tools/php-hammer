package net.rentalhost.plugins.services

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.jetbrains.php.lang.psi.PhpPsiElementFactory
import com.jetbrains.php.lang.psi.elements.PhpPsiElement
import com.jetbrains.php.lang.psi.elements.impl.ArrayCreationExpressionImpl
import com.jetbrains.php.lang.psi.elements.impl.ArrayHashElementImpl
import com.jetbrains.php.lang.psi.elements.impl.PhpPsiElementImpl

object ArrayService {
    fun unpackArray(array: ArrayCreationExpressionImpl): MutableList<PsiElement> {
        val arrayElements = mutableListOf<PsiElement>()

        for (arrayElement in array.children) {
            if (arrayElement is PhpPsiElementImpl<*> &&
                TypeService.isVariadic(arrayElement, ArrayCreationExpressionImpl::class.java)) {
                arrayElements.addAll(unpackArray(arrayElement.firstPsiChild as ArrayCreationExpressionImpl))
            }
            else if (arrayElement is ArrayHashElementImpl ||
                     arrayElement is PhpPsiElementImpl<*>) {
                arrayElements.add(arrayElement)
            }
        }

        return arrayElements
    }

    fun createArrayValue(project: Project, arrayHashElement: ArrayHashElementImpl): PhpPsiElement? {
        return PhpPsiElementFactory.createPhpPsiFromText(
            project,
            ArrayCreationExpressionImpl::class.java,
            "[${arrayHashElement.value?.text}]"
        ).firstPsiChild
    }
}
