package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.impl.ClassReferenceImpl
import com.jetbrains.php.lang.psi.elements.impl.MethodReferenceImpl
import net.rentalhost.plugins.services.FactoryService
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.ProblemsHolderService

class ToStringSimplificationInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object: PsiElementVisitor() {
        override fun visitElement(element: PsiElement) {
            if (element is MethodReferenceImpl &&
                (element.name ?: return).lowercase() == "__tostring") {
                val elementBase = element.firstPsiChild ?: return

                if (elementBase is ClassReferenceImpl &&
                    elementBase.text.lowercase() == "parent")
                    return

                ProblemsHolderService.registerProblem(
                    problemsHolder,
                    element,
                    "Call to __toString() can be simplified.",
                    LocalQuickFixService.SimpleInlineQuickFix("Replace with type cast (string)") {
                        element.replace(FactoryService.createTypeCast(problemsHolder.project, "string", elementBase.text))
                    }
                )
            }
        }
    }
}
