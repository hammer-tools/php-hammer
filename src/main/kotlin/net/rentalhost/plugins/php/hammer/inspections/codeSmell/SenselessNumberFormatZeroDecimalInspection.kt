package net.rentalhost.plugins.php.hammer.inspections.codeSmell

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.refactoring.suggested.createSmartPointer
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.BinaryExpression
import com.jetbrains.php.lang.psi.elements.FunctionReference
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.extensions.psi.isName
import net.rentalhost.plugins.php.hammer.services.FactoryService
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService

class SenselessNumberFormatZeroDecimalInspection : PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object : PhpElementVisitor() {
        override fun visitPhpFunctionCall(element: FunctionReference) {
            if (!element.isName("\\number_format") ||
                element.parameters.isEmpty()
            )
                return

            if (element.parameters.size < 4)
                return

            if (element.parameters[1].text != "0")
                return

            with(element.parameters[3] as? StringLiteralExpression ?: return) {
                if (contents != "")
                    return
            }

            val elementParameter = element.parameters[0]

            ProblemsHolderService.instance.registerProblem(
                problemsHolder,
                element,
                "senseless number_format() using zero decimal point",
                QuickFixService.instance.simpleReplace(
                    "Replace with type casting",
                    FactoryService.createTypeCastDouble(
                        problemsHolder.project, "string", "int",
                        if (elementParameter is BinaryExpression) "(${elementParameter.text})"
                        else elementParameter.text
                    ).createSmartPointer()
                )
            )
        }
    }
}
