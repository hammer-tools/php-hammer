package net.rentalhost.plugins.php.hammer.inspections.flowOptimization

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.refactoring.suggested.createSmartPointer
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.lexer.PhpTokenTypes
import com.jetbrains.php.lang.psi.elements.BinaryExpression
import com.jetbrains.php.lang.psi.elements.TernaryExpression
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.services.FormatterService
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService

class TernarySimplificationInspection : PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object : PhpElementVisitor() {
        override fun visitPhpTernaryExpression(expression: TernaryExpression) {
            if (expression.isShort)
                return

            val comparison = expression.condition

            if (comparison !is BinaryExpression)
                return

            val comparisonIdentical = comparison.operationType == PhpTokenTypes.opIDENTICAL
            val comparisonNotIdentical = comparison.operationType == PhpTokenTypes.opNOT_IDENTICAL

            if (!comparisonIdentical &&
                !comparisonNotIdentical
            )
                return

            val normalizedLeft = FormatterService.normalize(comparison.leftOperand ?: return)
            val normalizedRight = FormatterService.normalize(comparison.rightOperand ?: return)
            val normalizedTrue = FormatterService.normalize(expression.trueVariant ?: return)
            val normalizedFalse = FormatterService.normalize(expression.falseVariant ?: return)

            if (comparisonIdentical) {
                if (normalizedLeft != normalizedFalse ||
                    normalizedRight != normalizedTrue
                )
                    return
            } else if (normalizedLeft != normalizedTrue ||
                normalizedRight != normalizedFalse
            ) {
                return
            }

            ProblemsHolderService.instance.registerProblem(
                problemsHolder,
                expression,
                "ternary can be simplified",
                QuickFixService.instance.simpleReplace(
                    "Simplify ternary",
                    if (comparisonIdentical) (expression.falseVariant ?: return).createSmartPointer()
                    else (expression.trueVariant ?: return).createSmartPointer()
                )
            )
        }
    }
}
