package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.impl.ParameterImpl
import com.jetbrains.php.lang.psi.elements.impl.ParameterListImpl
import net.rentalhost.plugins.services.ProblemsHolderService

class ParameterDefaultsNullInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object: PsiElementVisitor() {
        override fun visitElement(element: PsiElement) {
            if (element is ParameterListImpl) {
                for (parameter in element.parameters) {
                    if (parameter is ParameterImpl &&
                        parameter.defaultValue != null) {
                        val defaultValue = parameter.defaultValueType

                        if (defaultValue.toString() != "null") {
                            ProblemsHolderService.registerProblem(
                                problemsHolder,
                                parameter,
                                "Default value of the parameter must be \"null\".",
                            )
                        }
                    }
                }
            }
        }
    }
}
