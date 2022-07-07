package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.refactoring.suggested.startOffset
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.impl.FunctionImpl
import com.jetbrains.php.lang.psi.elements.impl.PhpUseListImpl
import com.jetbrains.php.lang.psi.elements.impl.VariableImpl
import net.rentalhost.plugins.extensions.psi.*
import net.rentalhost.plugins.services.FactoryService
import net.rentalhost.plugins.services.ProblemsHolderService

class SortUseVariablesInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object: PsiElementVisitor() {
        override fun visitElement(element: PsiElement) {
            if (element is PhpUseListImpl) {
                val useVariables = element.getVariables()

                if (useVariables == null ||
                    useVariables.size < 2)
                    return

                val useContext = element.context as FunctionImpl

                val functionVariablesSorted = useContext.accessVariables()
                    .sortedBy { it.anchor.startOffset }
                    .distinctBy { it.variableName }

                val useVariablesNames = useVariables.map { it.name }

                val useVariablesSorted = useVariables
                    .sortedWith(compareBy(nullsLast()) { useVariable -> functionVariablesSorted.firstOrNull { it.variableName == useVariable.name }?.anchor?.startOffset })
                val useVariablesSortedNames = useVariablesSorted.map { it.name }

                if (useVariablesNames.toString() != useVariablesSortedNames.toString()) {
                    ProblemsHolderService.registerProblem(
                        problemsHolder,
                        element,
                        useVariables.declarationTextRange(element),
                        "Unorganized use() variables.",
                        SortByUsageQuickFix(useVariablesSorted)
                    )
                }
            }
        }
    }

    class SortByUsageQuickFix(
        private val useVariablesSorted: Collection<VariableImpl>,
    ): LocalQuickFix {
        override fun getFamilyName(): String = "Sort by usage"

        override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
            descriptor.psiElement.replace(
                FactoryService.createFunctionUse(
                    project,
                    useVariablesSorted.joinToString(", ") {
                        val useVariableName = "$${it.name}"

                        if (it.isRef()) "&$useVariableName"
                        else useVariableName
                    }
                )
            )
        }
    }
}
