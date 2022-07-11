package net.rentalhost.plugins.services

import com.intellij.psi.PsiElement
import com.jetbrains.php.lang.psi.elements.PhpPsiElement
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression
import com.jetbrains.php.lang.psi.elements.impl.*

object ElementService {
    fun conditionalStarter(element: PhpPsiElement): ControlStatementImpl? {
        if (element is IfImpl) {
            val elementParent = element.parent

            if (elementParent is ElseImpl) {
                return conditionalStarter(elementParent)
            }
        }
        else if (element is ElseImpl ||
                 element is ElseIfImpl) {
            val elementContext = element.context

            if (elementContext is IfImpl) {
                return conditionalStarter(elementContext)
            }
        }

        if (element !is ControlStatementImpl)
            return null

        return element
    }

    private fun isCompactFunction(element: PsiElement): Boolean =
        element is FunctionReferenceImpl &&
        element.name?.lowercase() == "compact"

    fun getCompactNames(element: PsiElement): List<String>? {
        if (isCompactFunction(element) &&
            element is FunctionReferenceImpl) {
            return element.parameters.map {
                if (it !is StringLiteralExpression)
                    return null

                return@map it.contents
            }
        }

        return null
    }
}
