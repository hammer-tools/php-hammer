package net.rentalhost.plugins.services

import com.intellij.openapi.project.Project
import com.jetbrains.php.lang.psi.PhpPsiElementFactory
import com.jetbrains.php.lang.psi.elements.PhpPsiElement
import com.jetbrains.php.lang.psi.elements.impl.ControlStatementImpl
import com.jetbrains.php.lang.psi.elements.impl.ElseIfImpl
import com.jetbrains.php.lang.psi.elements.impl.ElseImpl
import com.jetbrains.php.lang.psi.elements.impl.IfImpl

object ElementService {
    fun normalizeReturn(project: Project, text: String): String {
        val returnStatement = PhpPsiElementFactory.createReturnStatement(project, StringService.simplifyWhitespace(text))

        FormatterService.applyDefaults(returnStatement)

        return returnStatement.text
    }

    fun conditionalStarter(element: PhpPsiElement): PhpPsiElement? {
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

        return if (element is ControlStatementImpl) element else null
    }
}
