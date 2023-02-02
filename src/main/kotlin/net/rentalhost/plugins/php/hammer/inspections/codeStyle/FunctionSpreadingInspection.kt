package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.refactoring.suggested.createSmartPointer
import com.jetbrains.php.codeInsight.controlFlow.instructions.impl.PhpYieldInstructionImpl
import com.jetbrains.php.config.PhpLanguageLevel
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.lexer.PhpTokenTypes
import com.jetbrains.php.lang.psi.PhpPsiElementFactory
import com.jetbrains.php.lang.psi.PhpPsiUtil
import com.jetbrains.php.lang.psi.elements.ArrayCreationExpression
import com.jetbrains.php.lang.psi.elements.FunctionReference
import com.jetbrains.php.lang.psi.elements.impl.ConstantReferenceImpl
import com.jetbrains.php.lang.psi.elements.impl.FunctionImpl
import com.jetbrains.php.lang.psi.elements.impl.FunctionReferenceImpl
import com.jetbrains.php.lang.psi.resolve.types.PhpType
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.hammer.extensions.psi.hasInterface
import net.rentalhost.plugins.hammer.extensions.psi.isName
import net.rentalhost.plugins.hammer.extensions.psi.unpackValues
import net.rentalhost.plugins.hammer.services.ClassService
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService

fun isBadGenerator(function: FunctionReference): Boolean {
    val functionDeclaration = function.resolve() as? FunctionImpl ?: return true

    function.type.types.forEach {
        if (PhpType.isUnresolved(it)) {
            return@forEach
        }

        if (it.equals("\\Generator")) {
            val yieldInstructions = functionDeclaration.controlFlow.instructions
                .filterIsInstance(PhpYieldInstructionImpl::class.java)

            if (yieldInstructions.isEmpty()) return true

            yieldInstructions.forEach {
                if (PhpPsiUtil.isOfType(PhpPsiUtil.getNextSiblingIgnoreWhitespace(it.statement.argument, true), PhpTokenTypes.opHASH_ARRAY))
                    return true
            }
        }
        else {
            val functionReturnType = ClassService.findFQN(it, function.project) ?: return true

            if (!functionReturnType.hasInterface("\\Traversable") &&
                !PhpType.isArray(it) &&
                !PhpType.isPluralType(it))
                return true
        }
    }

    return false
}

class FunctionSpreadingInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        override fun visitPhpFunctionCall(function: FunctionReference) {
            if (function.isName("iterator_to_array") && function.parameters.isNotEmpty()) {
                functionIteratorToArray(function)
            }
            else if (function.isName("array_merge") && function.parameters.size >= 2) {
                functionArrayMerge(function)
            }
        }

        private fun functionIteratorToArray(function: FunctionReference) {
            val preserveKeys = function.getParameter(1)

            if (preserveKeys !is ConstantReferenceImpl ||
                preserveKeys.type.types.any { !it.equals(PhpType._FALSE) }) return

            registerProblem(function, arrayOf(function.getParameter(0) ?: return))
        }

        private fun functionArrayMerge(function: FunctionReference) {
            function.parameters.forEach {
                if (it is FunctionReferenceImpl && isBadGenerator(it)) return
            }

            registerProblem(function, function.parameters)
        }

        private fun registerProblem(function: FunctionReference, args: Array<PsiElement>) {
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
                                "[${args.joinToString(",") { e -> "...${e.text}" }}]"
                            ).unpackValues().joinToString(",") { e -> e.text }
                        }]"
                    ).createSmartPointer()
                )
            )
        }
    }

    override fun getMinimumSupportedLanguageLevel(): PhpLanguageLevel = PhpLanguageLevel.PHP740
}
