package net.rentalhost.plugins.php.hammer.inspections.codeSmell

import com.intellij.codeInspection.ProblemsHolder
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.BinaryExpression
import com.jetbrains.php.lang.psi.elements.FunctionReference
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.extensions.psi.isName
import net.rentalhost.plugins.services.FactoryService
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.ProblemsHolderService

class SenselessNumberFormatZeroDecimalInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        override fun visitPhpFunctionCall(element: FunctionReference) {
            if (!element.isName("number_format") ||
                element.parameters.isEmpty())
                return

            if (element.parameters.size != 1 &&
                element.parameters[1].text != "0")
                return

            val elementParameter = element.parameters[0]

            ProblemsHolderService.registerProblem(
                problemsHolder,
                element,
                "senseless number_format() using zero decimal point",
                LocalQuickFixService.SimpleReplaceQuickFix(
                    "Replace with type casting.",
                    FactoryService.createTypeCastDouble(
                        problemsHolder.project, "string", "int",
                        if (elementParameter is BinaryExpression) "(${elementParameter.text})"
                        else elementParameter.text
                    )
                )
            )
        }
    }
}
