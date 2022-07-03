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
                        val useVariablesNames = useVariables.toList()
                            .map { it.name }

                        val functionVariablesOrdered = elementContext.controlFlow.instructions.toList()
                            .filterIsInstance<PhpAccessVariableInstructionImpl>()
                            .filter { useVariablesNames.contains(it.variableName) }
                            .sortedBy { it.anchor.startOffset }
                            .map { it.variableName }
                            .distinct()

                        if (useVariablesNames.toString() != functionVariablesOrdered.toString()) {
                            val useVariablesFirst = useVariables.first()
                            val useVariablesStartsAt = VariableService.getLeafReference(useVariablesFirst) ?: useVariablesFirst

                            ProblemsHolderService.registerProblem(
                                problemsHolder,
                                element,
                                useVariablesStartsAt,
                                useVariables.last(),
                                "Unorganized use() variables.",
                                SortByUsageQuickFix(useVariables, functionVariablesOrdered)
                            )
                        }
                    }
                }
            }
        }
    }

    class SortByUsageQuickFix(
        private val useVariables: Collection<VariableImpl>,
        private val functionVariablesSorted: List<CharSequence>
    ): LocalQuickFix {
        override fun getFamilyName(): String {
            return "Sort by usage"
        }

        override fun applyFix(
            project: Project,
            descriptor: ProblemDescriptor
        ) {
            val useVariablesByName = useVariables
                .groupBy { it.name }
                .mapValues { it.value.first() }

            descriptor.psiElement.replace(
                FactoryService.createFunctionUse(
                    project,
                    functionVariablesSorted.joinToString(", ") {
                        val useVariable = useVariablesByName[it]
                        val useVariableName = "$${useVariable!!.name}"

                        if (VariableService.isLeafReference(useVariable)) "&$useVariableName"
                        else useVariableName
                    }
                )
            )
        }
    }
}
