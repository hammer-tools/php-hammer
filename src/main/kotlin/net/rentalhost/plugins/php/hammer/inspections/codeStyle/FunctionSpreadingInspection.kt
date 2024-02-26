package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.refactoring.suggested.createSmartPointer
import com.jetbrains.php.config.PhpLanguageLevel
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.PhpPsiElementFactory
import com.jetbrains.php.lang.psi.elements.ArrayCreationExpression
import com.jetbrains.php.lang.psi.elements.FunctionReference
import com.jetbrains.php.lang.psi.elements.impl.FunctionReferenceImpl
import com.jetbrains.php.lang.psi.elements.impl.VariableImpl
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.extensions.psi.*
import net.rentalhost.plugins.php.hammer.services.LanguageService
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService

class FunctionSpreadingInspection : PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object : PhpElementVisitor() {
        override fun visitPhpFunctionCall(function: FunctionReference) {
            if (!function.isName("\\array_merge") || function.parameters.size < 2) return

            val isBefore810 = !LanguageService.atLeast(problemsHolder.project, PhpLanguageLevel.PHP810)

            function.parameters.forEach {
                if (it.isVariadicPreceded()) return

                if (it is FunctionReferenceImpl) {
                    if (isBefore810) return
                    if (it.isGeneratorComplex()) return
                } else {
                    val variableResolved =
                        if (it is VariableImpl) (it.resolve() ?: return).parent.followContents()
                        else it

                    if (!variableResolved.isArrayCreation()) return
                    if (isBefore810 && (variableResolved as ArrayCreationExpression).isHashed()) return
                }
            }

            ProblemsHolderService.instance.registerProblem(
                problemsHolder,
                function,
                "function ${function.name}() can be replaced with spread",
                QuickFixService.instance.simpleReplace(
                    "Replace with spread",
                    PhpPsiElementFactory.createPhpPsiFromText(
                        function.project, ArrayCreationExpression::class.java, "[${
                            PhpPsiElementFactory.createPhpPsiFromText(
                                function.project,
                                ArrayCreationExpression::class.java,
                                "[${function.parameters.joinToString(",") { e -> "...${e.text}" }}]"
                            ).unpackValues().joinToString(",") { e -> e.text }
                        }]"
                    ).createSmartPointer()
                )
            )
        }
    }

    override fun getMinimumSupportedLanguageLevel(): PhpLanguageLevel = PhpLanguageLevel.PHP740
}
