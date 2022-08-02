package net.rentalhost.plugins.php.hammer.inspections.flowOptimization

import com.intellij.codeInspection.ProblemsHolder
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.PhpReturn
import com.jetbrains.php.lang.psi.elements.TernaryExpression
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.extensions.psi.unparenthesize
import net.rentalhost.plugins.services.ProblemsHolderService

class ReturnTernaryReplacementInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        override fun visitPhpTernaryExpression(ternary: TernaryExpression) {
            if (ternary.isShort)
                return

            val returnStatement = (ternary.unparenthesize() ?: return).parent as? PhpReturn ?: return

            ProblemsHolderService.registerProblem(
                problemsHolder,
                returnStatement.firstChild,
                "return-ternary can be replaced by if()"
            )
        }
    }
}
