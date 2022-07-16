package net.rentalhost.plugins.services

import com.intellij.psi.PsiElement
import kotlin.reflect.KClass
import kotlin.reflect.cast

object TreeService {
    private fun processChildren(element: PsiElement, processor: (PsiElement) -> Boolean) {
        var elementTesting = element.firstChild

        while (elementTesting != null) {
            if (!processor.invoke(elementTesting)) {
                processChildren(elementTesting, processor)
            }

            elementTesting = elementTesting.nextSibling
        }
    }

    fun getChildren(element: PsiElement, allowedClass: KClass<out PsiElement>): List<PsiElement> {
        val result: MutableList<PsiElement> = mutableListOf()

        processChildren(element) { elementTesting ->
            if (allowedClass.isInstance(elementTesting)) {
                result.add(allowedClass.cast(elementTesting))

                return@processChildren true
            }

            return@processChildren false
        }

        return result
    }
}
