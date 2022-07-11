package net.rentalhost.plugins.extensions.psi

import com.intellij.psi.PsiElement
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression
import com.jetbrains.php.lang.psi.elements.impl.ArrayCreationExpressionImpl
import com.jetbrains.php.lang.psi.elements.impl.ArrayHashElementImpl
import com.jetbrains.php.lang.psi.elements.impl.FunctionReferenceImpl
import com.jetbrains.php.lang.psi.elements.impl.PhpPsiElementImpl
import net.rentalhost.plugins.services.FactoryService
import net.rentalhost.plugins.services.TypeService

fun ArrayCreationExpressionImpl.unpackValues(): MutableList<PsiElement> {
    val arrayElements = mutableListOf<PsiElement>()

    for (arrayElement in children) {
        if (arrayElement is PhpPsiElementImpl<*> &&
            TypeService.isVariadic(arrayElement, ArrayCreationExpressionImpl::class.java)) {
            arrayElements.addAll((arrayElement.firstPsiChild as ArrayCreationExpressionImpl).unpackValues())
        }
        else if (arrayElement is PhpPsiElementImpl<*> &&
                 TypeService.isVariadic(arrayElement)) {
            val variadicElement = arrayElement.firstPsiChild

            if (variadicElement is FunctionReferenceImpl &&
                variadicElement.name?.lowercase() == "compact") {
                val compactVariables = mutableListOf<PsiElement>()

                for (compactArgument in variadicElement.parameters) {
                    if (compactArgument !is StringLiteralExpression) {
                        arrayElements.add(arrayElement)

                        continue
                    }

                    compactVariables.add(
                        FactoryService.createArrayValue(
                            this.project,
                            "'${compactArgument.contents}'=>\$${compactArgument.contents}"
                        ) as PsiElement
                    )
                }

                arrayElements.addAll(compactVariables)

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
