package net.rentalhost.plugins.php.hammer.inspections.codeSmell

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.refactoring.suggested.createSmartPointer
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.FunctionReference
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression
import com.jetbrains.php.lang.psi.elements.impl.ArrayCreationExpressionImpl
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.extensions.psi.isName
import net.rentalhost.plugins.php.hammer.services.ElementService
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService

class CompactDuplicatedTermsInspection : PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object : PhpElementVisitor() {
        override fun visitPhpFunctionCall(element: FunctionReference) {
            if (!element.isName("\\compact"))
                return

            val variables = mutableListOf<String>()

            element.parameters
                .map {
                    when (it) {
                        is ArrayCreationExpressionImpl -> it.values().toList()
                        else -> listOf(it)
                    }
                }
                .forEach { values ->
                    values
                        .filterIsInstance<StringLiteralExpression>()
                        .forEach forEachValues@{
                            if (!variables.contains(it.contents)) {
                                variables.add(it.contents)
                                return@forEachValues
                            }

                            val itPointer = it.createSmartPointer()

                            ProblemsHolderService.instance.registerProblem(
                                problemsHolder, it,
                                "duplicated term in compact()",
                                QuickFixService.instance.simpleInline("Drop duplicated term") {
                                    with(itPointer.element ?: return@simpleInline) {
                                        ElementService.dropCompactArgument(this)
                                    }
                                }
                            )
                        }
                }
        }
    }
}
