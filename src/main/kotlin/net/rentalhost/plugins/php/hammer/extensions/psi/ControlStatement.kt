package net.rentalhost.plugins.php.hammer.extensions.psi

import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IElementType
import com.intellij.psi.util.elementType
import com.jetbrains.php.lang.lexer.PhpTokenTypes
import com.jetbrains.php.lang.psi.elements.*

private fun isSimplified(element: PsiElement?, exceptBy: IElementType): Boolean =
    element !is BinaryExpression ||
            (element.operation.elementType != exceptBy &&
                    isSimplified(element.leftOperand.unparenthesize(), exceptBy) &&
                    isSimplified(element.rightOperand.unparenthesize(), exceptBy))

fun ControlStatement.isOrSimplified(): Boolean =
    isSimplified(this.condition.unparenthesize(), PhpTokenTypes.opAND)

fun ControlStatement.isAndSimplified(): Boolean =
    isSimplified(this.condition.unparenthesize(), PhpTokenTypes.opOR)

fun ControlStatement.getNextSiblingConditional(): ControlStatement? {
    return when (this) {
        is If -> {
            when {
                this.elseIfBranches.isNotEmpty() -> this.elseIfBranches.first()
                this.elseBranch != null -> (this.elseBranch ?: return null).statement as? If ?: return null
                else -> this.getNextTreePsiSibling() as? ControlStatement ?: return null
            }
        }

        is ElseIf -> {
            with(this.getNextTreePsiSibling()) {
                if (this is ControlStatement) this
                else (this as? Else ?: return null).statement as? If ?: return null
            }
        }

        else -> return null
    }
}
