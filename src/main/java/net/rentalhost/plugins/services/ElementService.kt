package net.rentalhost.plugins.services

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.jetbrains.php.lang.psi.PhpPsiElementFactory
import com.jetbrains.php.lang.psi.PhpPsiUtil
import com.jetbrains.php.lang.psi.elements.GroupStatement
import com.jetbrains.php.lang.psi.elements.PhpPsiElement
import com.jetbrains.php.lang.psi.elements.impl.*

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

    fun functionBody(element: FunctionImpl): GroupStatementImpl? =
        PhpPsiUtil.getChildByCondition(element, GroupStatement.INSTANCEOF)

    private fun addBefore(addIt: PsiElement, beforeIt: PsiElement): PsiElement =
        beforeIt.parent.addBefore(addIt, beforeIt)

    fun addBeforeOrThen(addIt: PsiElement, beforeIt: PsiElement?, orThen: Lazy<PsiElement>): PsiElement =
        if (beforeIt == null) orThen.value
        else addBefore(addIt, beforeIt)
}
