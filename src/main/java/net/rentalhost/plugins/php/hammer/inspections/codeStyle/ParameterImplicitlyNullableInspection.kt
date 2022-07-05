package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.jetbrains.php.config.PhpLanguageLevel
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.impl.ParameterImpl
import net.rentalhost.plugins.services.ProblemsHolderService

class ParameterImplicitlyNullableInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object: PsiElementVisitor() {
        override fun visitElement(element: PsiElement) {
            if (element is ParameterImpl) {
                if (!element.defaultValueType.isNullable)
                    return

                val declaredType = element.declaredType

                if (declaredType.isNullable ||
                    declaredType.isEmpty)
                    return

                ProblemsHolderService.registerProblem(
                    problemsHolder,
                    element,
                    "Parameter type is implicitly null."
                )
            }
        }
    }

    override fun getMinimumSupportedLanguageLevel(): PhpLanguageLevel = PhpLanguageLevel.PHP800
}
