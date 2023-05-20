package net.rentalhost.plugins.php.hammer.services

import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.containers.isEmpty
import com.jetbrains.php.lang.psi.elements.ArrayHashElement
import com.jetbrains.php.lang.psi.elements.ParameterList
import com.jetbrains.php.lang.psi.elements.PhpPsiElement
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression
import com.jetbrains.php.lang.psi.elements.impl.*
import net.rentalhost.plugins.php.hammer.extensions.psi.delete
import net.rentalhost.plugins.php.hammer.extensions.psi.getCommaRange

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

    fun getCompactNames(element: PsiElement): List<String>? {
        if (element is FunctionReferenceImpl &&
            (element.name ?: return null).lowercase() == "compact") {
            return element.parameters.map {
                if (it !is StringLiteralExpression)
                    return null

                return@map it.contents
            }
        }

        return null
    }

    fun dropCompactArgument(it: PsiElement) {
        val arrayExpression = PsiTreeUtil.getParentOfType(it, ArrayCreationExpressionImpl::class.java, false, ParameterList::class.java)

        val range = when {
            arrayExpression != null -> with(it.parent) {
                if (this.parent is ArrayHashElement) this.parent
                else this
            }

            else -> it
        }

        range.getCommaRange().delete()

        if (arrayExpression is ArrayCreationExpressionImpl &&
            arrayExpression.values().isEmpty()) {
            arrayExpression.getCommaRange().delete()
        }
    }
}
