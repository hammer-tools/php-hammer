package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.lexer.PhpTokenTypes
import com.jetbrains.php.lang.psi.elements.PhpPsiElement
import com.jetbrains.php.lang.psi.elements.impl.StringLiteralExpressionImpl
import com.jetbrains.php.lang.psi.elements.impl.VariableImpl
import net.rentalhost.plugins.services.FactoryService
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.ProblemsHolderService

class StringSimplificationInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object: PsiElementVisitor() {
        override fun visitElement(element: PsiElement) {
            if (element !is VariableImpl)
                return

            val parent = element.parent

            if (parent !is StringLiteralExpressionImpl ||
                element.prevSibling !== parent.firstChild ||
                element.nextSibling !== parent.lastChild)
                return

            val elementText = element.text
            val elementTextCropped = elementText.substringAfter("{").substringBefore("}")
            val elementTextNormalized =
                if (elementText.startsWith("\${")) "\$$elementTextCropped"
                else elementTextCropped

            val isArrayKey =
                if (parent.parent is PhpPsiElement) PsiTreeUtil.skipWhitespacesAndCommentsForward(parent.parent).elementType == PhpTokenTypes.opHASH_ARRAY
                else false

            ProblemsHolderService.registerProblem(
                problemsHolder,
                parent,
                "String can be simplified.",
                LocalQuickFixService.SimpleReplaceQuickFix(
                    "Replace with type cast (string)",
                    if (isArrayKey) FactoryService.createExpression(problemsHolder.project, elementTextNormalized)
                    else FactoryService.createTypeCastExpression(problemsHolder.project, "string", elementTextNormalized)
                )
            )
        }
    }
}
