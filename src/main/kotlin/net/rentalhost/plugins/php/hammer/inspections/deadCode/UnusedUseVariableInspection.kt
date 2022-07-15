package net.rentalhost.plugins.php.hammer.inspections.deadCode

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.SmartPointerManager
import com.intellij.psi.SmartPsiElementPointer
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.impl.FunctionImpl
import com.jetbrains.php.lang.psi.elements.impl.PhpUseListImpl
import com.jetbrains.php.lang.psi.elements.impl.VariableImpl
import net.rentalhost.plugins.extensions.psi.*
import net.rentalhost.plugins.services.ProblemsHolderService

class UnusedUseVariableInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object: PsiElementVisitor() {
        override fun visitElement(element: PsiElement) {
            if (element !is PhpUseListImpl)
                return

            val useVariables = element.getVariables()

            if (useVariables.isNullOrEmpty())
                return

            val useContext = element.context as FunctionImpl

            val functionVariables = useContext.accessVariables()
                .map { it.variableName }
                .distinct()

            useVariables
                .filterNot { functionVariables.contains(it.name) }
                .forEach {
                    ProblemsHolderService.registerProblem(
                        problemsHolder,
                        element,
                        it.declarationTextRange(element),
                        "Unused variable declared in use().",
                        DeleteUnusedVariableDeclarationQuickFix(SmartPointerManager.createPointer(it)),
                        ProblemHighlightType.LIKE_UNUSED_SYMBOL
                    )
                }
        }
    }

    class DeleteUnusedVariableDeclarationQuickFix(
        private val useVariable: SmartPsiElementPointer<VariableImpl>,
    ): LocalQuickFix {
        override fun getFamilyName(): String =
            "Delete unused variable"

        override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
            val useElement = descriptor.psiElement as PhpUseListImpl
            val useVariables = useElement.getVariables() ?: return

            if (useVariables.size == 1) {
                useElement.delete()

                return
            }

            (useVariable.element ?: return).declarationChildRange(true).delete()

            useElement.deleteTrailingComma()
        }
    }
}
