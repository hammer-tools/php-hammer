package net.rentalhost.plugins.php.hammer.inspections.codeSmell

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.impl.FunctionReferenceImpl
import net.rentalhost.plugins.services.ProblemsHolderService

class SenselessNumberFormatZeroDecimalInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object: PsiElementVisitor() {
        override fun visitElement(element: PsiElement) {
            if (element !is FunctionReferenceImpl ||
                (element.name ?: return).lowercase() != "number_format")
                return

            if (element.parameters.isEmpty())
                return

            if (element.parameters.size != 1 &&
                element.parameters[1].text != "0")
                return

            ProblemsHolderService.registerProblem(
                problemsHolder,
                element,
                "Senseless number_format() using zero decimal point."
            )
        }
    }
}
