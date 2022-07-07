package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil
import com.jetbrains.php.config.PhpLanguageLevel
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.impl.FunctionImpl
import net.rentalhost.plugins.extensions.psi.accessVariables
import net.rentalhost.plugins.extensions.psi.isAnonymous
import net.rentalhost.plugins.extensions.psi.isStatic
import net.rentalhost.plugins.extensions.psi.variableName
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
                    "Function can be static."
                )
            }
        }
    }

    override fun getMinimumSupportedLanguageLevel(): PhpLanguageLevel = PhpLanguageLevel.PHP540
}
