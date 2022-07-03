package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.refactoring.suggested.startOffset
import com.jetbrains.php.codeInsight.controlFlow.instructions.impl.PhpAccessVariableInstructionImpl
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.impl.FunctionImpl
import com.jetbrains.php.lang.psi.elements.impl.PhpUseListImpl
import com.jetbrains.php.lang.psi.elements.impl.VariableImpl
import net.rentalhost.plugins.services.FactoryService
import net.rentalhost.plugins.services.ProblemsHolderService
import net.rentalhost.plugins.services.VariableService

class SortUseVariablesInspection: PhpInspection() {
    override fun buildVisitor(
        problemsHolder: ProblemsHolder,
        isOnTheFly: Boolean
    ): PsiElementVisitor {
        return object: PsiElementVisitor() {
            override fun visitElement(element: PsiElement) {
                if (element is PhpUseListImpl) {
                    val elementContext = element.context

                    if (elementContext is FunctionImpl) {
                        val useVariables = PsiTreeUtil.findChildrenOfType(element, VariableImpl::class.java)

                        if (useVariables.size < 2)
                            return

                        val functionVariablesSorted = elementContext.controlFlow.instructions
                            .filterIsInstance<PhpAccessVariableInstructionImpl>()
                            .sortedBy { it.anchor.startOffset }
                            .distinctBy { it.variableName }

                        val useVariablesNames = useVariables.map { it.name }

                        val useVariablesSorted = useVariables
                            .sortedWith(compareBy(nullsLast()) { useVariable -> functionVariablesSorted.firstOrNull { it.variableName == useVariable.name }?.anchor?.startOffset })
                        val useVariablesSortedNames = useVariablesSorted.map { it.name }

                        if (useVariablesNames.toString() != useVariablesSortedNames.toString()) {
                            val useVariablesFirst = useVariables.first()
                            val useVariablesStartsAt = VariableService.getLeafReference(useVariablesFirst) ?: useVariablesFirst

                            ProblemsHolderService.registerProblem(
                                problemsHolder,
                                element,
                                useVariablesStartsAt,
                                useVariables.last(),
                                "Unorganized use() variables.",
                                SortByUsageQuickFix(useVariablesSorted)
                            )
                        }
                    }
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

                        if (VariableService.isLeafReference(it)) "&$useVariableName"
                        else useVariableName
                    }
                )
            )
        }
    }
}
