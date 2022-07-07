package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil
import com.jetbrains.php.config.PhpLanguageLevel
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.impl.FunctionImpl
import net.rentalhost.plugins.extensions.psi.*
import net.rentalhost.plugins.services.FactoryService
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.ProblemsHolderService

class AnonymousFunctionStaticInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object: PsiElementVisitor() {
        override fun visitElement(element: PsiElement) {
            if (element is FunctionImpl &&
                element.isAnonymous() &&
                !element.isStatic()) {
                val elementScopes = mutableListOf(element)

                elementScopes.addAll(PsiTreeUtil.findChildrenOfType(element, FunctionImpl::class.java))

                for (elementScope in elementScopes) {
                    if (elementScope.accessVariables().find { it.variableName == "this" } != null) {
                        return
                    }
                }

                ProblemsHolderService.registerProblem(
                    problemsHolder,
                    element.firstChild,
                    "This anonymous function can be static.",
                    LocalQuickFixService.SimpleInlineQuickFix(
                        "Make this function static",
                        applyFix = { element.insertBefore(FactoryService.createStaticKeyword(problemsHolder.project)) }
                    )
                )
            }
        }
    }

    override fun getMinimumSupportedLanguageLevel(): PhpLanguageLevel = PhpLanguageLevel.PHP540
}
