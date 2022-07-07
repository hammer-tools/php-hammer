package net.rentalhost.plugins.services

import com.jetbrains.php.lang.psi.elements.PhpPsiElement
import com.jetbrains.php.lang.psi.elements.impl.ControlStatementImpl
import com.jetbrains.php.lang.psi.elements.impl.ElseIfImpl
import com.jetbrains.php.lang.psi.elements.impl.ElseImpl
import com.jetbrains.php.lang.psi.elements.impl.IfImpl

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
}
