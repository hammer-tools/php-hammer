package net.rentalhost.plugins.php.hammer.inspections.flowOptimization

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.refactoring.suggested.createSmartPointer
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.PhpReturn
import com.jetbrains.php.lang.psi.elements.TernaryExpression
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.hammer.extensions.psi.insertAfter
import net.rentalhost.plugins.hammer.extensions.psi.unparenthesize
import net.rentalhost.plugins.hammer.services.FactoryService
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService

class ReturnTernaryReplacementInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        override fun visitPhpTernaryExpression(ternary: TernaryExpression) {
            if (ternary.isShort)
                return

            val returnStatement = (ternary.unparenthesize() ?: return).parent as? PhpReturn ?: return
            val returnStatementPointer = returnStatement.createSmartPointer()

            val ternaryPointer = ternary.createSmartPointer()

            ProblemsHolderService.instance.registerProblem(
                problemsHolder,
                returnStatement.firstChild,
                "return-ternary can be replaced by if()",
                QuickFixService.instance.simpleInline("Replace by if()") {
                    val ternaryLocal = ternaryPointer.element ?: return@simpleInline
                    val returnStatementLocal = returnStatementPointer.element ?: return@simpleInline

                    returnStatementLocal.insertAfter(
                        FactoryService.createReturn(
                            problemsHolder.project,
                            ((ternaryLocal.falseVariant ?: return@simpleInline).unparenthesize() ?: return@simpleInline).text
                        )
                    )

                    returnStatementLocal.replace(
                        FactoryService.createIfConditional(
                            problemsHolder.project,
                            (ternaryLocal.condition.unparenthesize() ?: return@simpleInline).text,
                            ((ternaryLocal.trueVariant ?: return@simpleInline).unparenthesize() ?: return@simpleInline).text,
                        )
                    )
                }
            )
        }
    }
}
