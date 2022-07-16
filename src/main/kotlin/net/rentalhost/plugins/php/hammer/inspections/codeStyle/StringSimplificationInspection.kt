package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.jetbrains.php.lang.inspections.PhpInspection
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
            val elementTextNormalized = elementText.substringAfter("{").substringBefore("}")

            ProblemsHolderService.registerProblem(
                problemsHolder,
                parent,
                "String can be simplified.",
                LocalQuickFixService.SimpleReplaceQuickFix(
                    "Replace with type cast (string)",
                    FactoryService.createTypeCastExpression(
                        problemsHolder.project, "string",
                        if (elementText.startsWith("\${")) "\$$elementTextNormalized"
                        else elementTextNormalized
                    )
                )
            )
        }
    }
}
