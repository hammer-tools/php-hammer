package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInsight.intention.FileModifier.SafeFieldForPreview
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.project.Project
import com.intellij.psi.SmartPsiElementPointer
import com.intellij.refactoring.suggested.createSmartPointer
import com.intellij.refactoring.suggested.startOffset
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.PhpUseList
import com.jetbrains.php.lang.psi.elements.impl.FunctionImpl
import com.jetbrains.php.lang.psi.elements.impl.VariableImpl
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.extensions.psi.*
import net.rentalhost.plugins.php.hammer.services.FactoryService
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService

class SortUseVariablesInspection : PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object : PhpElementVisitor() {
        override fun visitPhpUseList(element: PhpUseList) {
            val useVariables = element.getVariables()

            if (useVariables == null ||
                useVariables.size < 2
            )
                return

            val useContext = element.context as FunctionImpl

            val functionVariablesSorted = useContext.accessVariables()
                .sortedBy { it.anchor.startOffset }
                .distinctBy { it.variableName }

            val useVariablesNames = useVariables.map { it.name }

            val useVariablesSorted = useVariables
                .sortedWith(compareBy(nullsLast()) { useVariable -> functionVariablesSorted.firstOrNull { it.variableName == useVariable.name }?.anchor?.startOffset })
            val useVariablesSortedNames = useVariablesSorted.map { it.name }

            if (useVariablesNames.toString() == useVariablesSortedNames.toString())
                return

            ProblemsHolderService.instance.registerProblem(
                problemsHolder,
                element,
                useVariables.declarationTextRange(element),
                "use() variables can be sorted",
                SortByUsageQuickFix(useVariablesSorted.map { it.createSmartPointer() })
            )
        }
    }

    class SortByUsageQuickFix(
        @SafeFieldForPreview private val useVariablesSorted: Collection<SmartPsiElementPointer<VariableImpl>>,
    ) : QuickFixService.SimpleQuickFix("Sort by usage") {
        override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
            descriptor.psiElement.replace(
                FactoryService.createFunctionUse(
                    project,
                    useVariablesSorted.mapNotNull { it.element }.joinToString(", ") {
                        val useVariableName = "$${it.name}"

                        if (it.isRef()) "&$useVariableName"
                        else useVariableName
                    }
                )
            )
        }
    }
}
