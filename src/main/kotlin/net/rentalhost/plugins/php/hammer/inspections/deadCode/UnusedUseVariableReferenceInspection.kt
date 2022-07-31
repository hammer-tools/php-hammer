package net.rentalhost.plugins.php.hammer.inspections.deadCode

import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.PhpUseList
import com.jetbrains.php.lang.psi.elements.impl.AssignmentExpressionImpl
import com.jetbrains.php.lang.psi.elements.impl.FunctionImpl
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.extensions.psi.*
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.ProblemsHolderService

class UnusedUseVariableReferenceInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        override fun visitPhpUseList(element: PhpUseList) {
            val useVariables = with(element.getVariables()) {
                if (isNullOrEmpty())
                    return

                filter { it.isRef() }
            }

            val useContext = element.context as FunctionImpl

            val useContextAssignment = lazy {
                with(useContext.parent.parent) {
                    if (this is AssignmentExpressionImpl) this.variable?.name
                    else null
                }
            }

            val functionVariablesWriting = useContext.accessMutableVariables().names()

            for (useVariable in useVariables) {
                if (!functionVariablesWriting.contains(useVariable.name)) {
                    if (useContextAssignment.value == useVariable.name)
                        continue

                    val functionContext = PsiTreeUtil.getParentOfType(useContext, FunctionImpl::class.java)

                    if (functionContext is FunctionImpl) {
                        val functionContextVariablesWriting = functionContext.accessMutableVariables().names()

                        if (functionContextVariablesWriting.contains(useVariable.name)) {
                            continue
                        }
                    }

                    with(useVariable.getLeafRef() as PsiElement) {
                        ProblemsHolderService.registerProblem(
                            problemsHolder,
                            this,
                            "unused reference for variable declared in use()",
                            LocalQuickFixService.SimpleDeleteQuickFix("Delete reference indicator (\"&\")"),
                            ProblemHighlightType.LIKE_UNUSED_SYMBOL
                        )
                    }
                }
            }
        }
    }
}
