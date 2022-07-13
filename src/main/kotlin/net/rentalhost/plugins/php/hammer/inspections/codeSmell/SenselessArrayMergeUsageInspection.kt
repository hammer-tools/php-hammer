package net.rentalhost.plugins.php.hammer.inspections.codeSmell

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.impl.ArrayCreationExpressionImpl
import com.jetbrains.php.lang.psi.elements.impl.FunctionReferenceImpl
import net.rentalhost.plugins.extensions.psi.isVariadic
import net.rentalhost.plugins.services.FactoryService
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.ProblemsHolderService

class SenselessArrayMergeUsageInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object: PsiElementVisitor() {
        override fun visitElement(element: PsiElement) {
            if (element is FunctionReferenceImpl &&
                (element.name ?: return).lowercase() == "array_merge") {
                val elementSimplified = when (element.parameters.size) {
                    0 -> FactoryService.createArrayEmpty(problemsHolder.project)
                    1 -> element.parameters[0]
                    else -> return
                }

                if (elementSimplified is ArrayCreationExpressionImpl &&
                    elementSimplified.isVariadic())
                    return

                ProblemsHolderService.registerProblem(
                    problemsHolder,
                    element,
                    "Senseless array_merge() usage.",
                    LocalQuickFixService.SimpleReplaceQuickFix(
                        "Simplify useless array_merge()",
                        elementSimplified
                    )
                )
            }
        }
    }
}
