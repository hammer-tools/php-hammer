package net.rentalhost.plugins.extensions.psi

import com.intellij.psi.PsiElement
import com.jetbrains.php.lang.psi.elements.impl.ArrayCreationExpressionImpl
import com.jetbrains.php.lang.psi.elements.impl.ArrayHashElementImpl
import com.jetbrains.php.lang.psi.elements.impl.PhpPsiElementImpl
import net.rentalhost.plugins.services.TypeService

fun ArrayCreationExpressionImpl.unpackValues(): MutableList<PsiElement> {
    val arrayElements = mutableListOf<PsiElement>()

    for (arrayElement in children) {
        if (arrayElement is PhpPsiElementImpl<*> &&
            TypeService.isVariadic(arrayElement, ArrayCreationExpressionImpl::class.java)) {
            arrayElements.addAll((arrayElement.firstPsiChild as ArrayCreationExpressionImpl).unpackValues())
        }
        else if (arrayElement is ArrayHashElementImpl ||
                 arrayElement is PhpPsiElementImpl<*>) {
            arrayElements.add(arrayElement)
        }
    }

    return arrayElements
}
