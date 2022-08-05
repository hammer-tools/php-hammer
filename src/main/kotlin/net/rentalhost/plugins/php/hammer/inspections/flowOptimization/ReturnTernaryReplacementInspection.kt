package net.rentalhost.plugins.php.hammer.inspections.flowOptimization

import com.intellij.codeInspection.ProblemsHolder
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

            ProblemsHolderService.instance.registerProblem(
                problemsHolder,
                returnStatement.firstChild,
                "return-ternary can be replaced by if()",
                QuickFixService.instance.simpleInline("Replace by if()") {
                    returnStatement.insertAfter(
                        FactoryService.createReturn(
                            problemsHolder.project,
                            ((ternary.falseVariant ?: return@simpleInline).unparenthesize() ?: return@simpleInline).text
                        )
                    )

                    returnStatement.replace(
                        FactoryService.createIfConditional(
                            problemsHolder.project,
                            (ternary.condition.unparenthesize() ?: return@simpleInline).text,
                            ((ternary.trueVariant ?: return@simpleInline).unparenthesize() ?: return@simpleInline).text,
                        )
                    )
                }
            )
        }
    }
}
