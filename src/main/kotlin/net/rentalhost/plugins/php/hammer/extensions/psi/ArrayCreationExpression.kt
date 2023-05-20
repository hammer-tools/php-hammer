package net.rentalhost.plugins.php.hammer.extensions.psi

import com.intellij.psi.PsiElement
import com.jetbrains.php.lang.psi.elements.ArrayCreationExpression
import com.jetbrains.php.lang.psi.elements.impl.ArrayHashElementImpl
import com.jetbrains.php.lang.psi.elements.impl.PhpPsiElementImpl
import net.rentalhost.plugins.php.hammer.services.ElementService
import net.rentalhost.plugins.php.hammer.services.FactoryService
import net.rentalhost.plugins.php.hammer.services.TypeService

fun ArrayCreationExpression.unpackValues(): MutableList<PsiElement> {
  val arrayElements = mutableListOf<PsiElement>()

  for (arrayElement in children) {
    if (arrayElement is PhpPsiElementImpl<*> &&
      TypeService.isVariadic(arrayElement, ArrayCreationExpression::class.java)) {
      arrayElements.addAll((arrayElement.firstPsiChild as ArrayCreationExpression).unpackValues())
    }
    else if (arrayElement is PhpPsiElementImpl<*> &&
      TypeService.isVariadic(arrayElement)) {
      val compactNames = ElementService.getCompactNames(arrayElement.firstPsiChild as PsiElement)

      if (compactNames != null) {
        arrayElements.addAll(compactNames.map { FactoryService.createArrayKeyValue(arrayElement.project, "'$it'", "\$$it") })

        continue
      }

      arrayElements.add(arrayElement)
    }
    else if (arrayElement is ArrayHashElementImpl ||
      arrayElement is PhpPsiElementImpl<*>) {
      arrayElements.add(arrayElement)
    }
  }

  return arrayElements
}
