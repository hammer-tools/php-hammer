package net.rentalhost.plugins.php.hammer.extensions.psi

import com.intellij.psi.PsiElement
import com.jetbrains.php.codeInsight.controlFlow.instructions.impl.PhpYieldInstructionImpl
import com.jetbrains.php.lang.lexer.PhpTokenTypes
import com.jetbrains.php.lang.psi.PhpPsiUtil
import com.jetbrains.php.lang.psi.elements.Function
import com.jetbrains.php.lang.psi.elements.FunctionReference
import com.jetbrains.php.lang.psi.elements.impl.FunctionImpl
import com.jetbrains.php.lang.psi.elements.impl.PhpYieldImpl
import com.jetbrains.php.lang.psi.elements.impl.UnaryExpressionImpl
import com.jetbrains.php.lang.psi.resolve.types.PhpType
import net.rentalhost.plugins.php.hammer.services.ClassService

fun FunctionReference.getErrorControlOperator(): PsiElement? =
    with(this.parent) {
        return if (this is UnaryExpressionImpl &&
            this.firstChild.text == "@"
        ) return this.firstChild
        else null
    }

fun FunctionReference.isName(expectedName: String): Boolean {
    with(resolve() as? Function ?: return false) {
        return fqn.equals(expectedName, true)
    }
}

fun FunctionReference.isGeneratorComplex(): Boolean {
    val functionDeclaration = this.resolve() as? FunctionImpl ?: return true

    this.type.types.forEach { it ->
        if (PhpType.isUnresolved(it)) {
            return@forEach
        }

        if (it.equals("\\Generator")) {
            val yieldInstructions = functionDeclaration.controlFlow.instructions
                .filterIsInstance<PhpYieldInstructionImpl>()

            if (yieldInstructions.isEmpty()) return true

            yieldInstructions.forEach {
                if (PhpPsiUtil.isOfType(PhpPsiUtil.getNextSiblingIgnoreWhitespace(it.statement.argument, true), PhpTokenTypes.opHASH_ARRAY))
                    return true

                if (PhpYieldImpl.getFrom(it.statement) != null) {
                    val fromStatement = it.statement.argument

                    if (fromStatement is FunctionReference &&
                        fromStatement.isGeneratorComplex()
                    ) return true
                }
            }
        } else {
            val functionReturnType = ClassService.findFQN(it, this.project)

            if (functionReturnType?.hasInterface("\\Traversable") != true &&
                !PhpType.isArray(it) &&
                !PhpType.isPluralType(it)
            )
                return true
        }
    }

    return false
}
