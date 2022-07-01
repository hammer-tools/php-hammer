package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.refactoring.suggested.startOffset
import com.jetbrains.php.codeInsight.controlFlow.instructions.impl.PhpAccessVariableInstructionImpl
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.impl.FunctionImpl
import com.jetbrains.php.lang.psi.elements.impl.PhpUseListImpl
import com.jetbrains.php.lang.psi.elements.impl.VariableImpl
import net.rentalhost.plugins.services.ProblemsHolderService

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

                        val functionFlow = elementContext.controlFlow
                        val functionFlowAccesses = functionFlow.instructions.toList()
                            .filterIsInstance<PhpAccessVariableInstructionImpl>()
                            .filter { useVariablesNames.contains(it.variableName) }
                            .sortedBy { it.anchor.startOffset }
                            .map { it.variableName }
                            .distinct()

                        if (useVariablesNames.toString() != functionFlowAccesses.toString()) {
                            ProblemsHolderService.registerProblem(
                                problemsHolder,
                                element,
                                useVariables.first(),
                                useVariables.last(),
                                "Unorganized use() variables."
                            )
                        }
                    }
                }
            }
        }
    }
}
