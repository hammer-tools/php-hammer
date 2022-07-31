package net.rentalhost.plugins.extensions.psi

import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType
import com.jetbrains.php.lang.lexer.PhpTokenTypes
import com.jetbrains.php.lang.parser.PhpElementTypes
import com.jetbrains.php.lang.psi.elements.*
import com.jetbrains.php.lang.psi.elements.impl.StatementImpl
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

fun PsiElement?.insertBeforeElse(addIt: PsiElement, orElse: Lazy<() -> PsiElement>): PsiElement =
    this?.parent?.addBefore(addIt, this) ?: orElse.value.invoke()

fun PsiElement.insertBefore(addIt: PsiElement): PsiElement =
    this.parent.addBefore(addIt, this)

fun PsiElement.replaceWithGroupStatement(groupStatement: GroupStatement) {
    val elementReplacement = replace(groupStatement)

    elementReplacement.firstChild.delete()
    elementReplacement.lastChild.delete()

    val elementReplacementParent = elementReplacement.parent

    if (elementReplacementParent is Statement) {
        elementReplacementParent.rebuild()
    }
}

fun PsiElement.swap(swapWith: PsiElement): Unit = with(copy()) {
    this@swap.replace(swapWith)
    swapWith.replace(this)
}

fun PsiElement.isVariadicPreceded(): Boolean {
    return (PsiTreeUtil.skipWhitespacesAndCommentsBackward(this) ?: return false)
        .node.elementType == PhpTokenTypes.opVARIADIC
}

fun PsiElement.isStub(): Boolean =
    containingFile.virtualFile.path.contains("/php.jar!/")

@OptIn(ExperimentalContracts::class)
inline fun <reified T: PsiElement> PsiElement?.isExactly(): Boolean {
    contract { returns(true) implies (this@isExactly is T) }

    return this != null && this::class == T::class
}

fun PsiElement.isStrictlyStatement(): Boolean =
    isExactly<StatementImpl>()

fun PsiElement.withOptionalNotOperator(): PsiElement {
    val elementParent = this.parent as? UnaryExpression ?: return this

    return if (elementParent.operation.elementType === PhpTokenTypes.opNOT) parent
    else this
}

fun PsiElement?.unparenthesize(): PsiElement? =
    if (this is ParenthesizedExpression) this.argument.unparenthesize()
    else this

fun PsiElement.unwrapStatement(): PsiElement {
    return if (isExactly<StatementImpl>()) (this.firstPsiChild ?: return this).unwrapStatement()
    else this
}

fun PsiElement.followContents(): PsiElement = with(unparenthesize()) {
    if (this is AssignmentExpression) return@with value.unparenthesize()
    else this
} ?: this

fun PsiElement.getCommaRange(): Pair<PsiElement, PsiElement> {
    val leftComma = PsiTreeUtil.skipWhitespacesAndCommentsBackward(this)

    if (leftComma.elementType == PhpTokenTypes.opCOMMA)
        return Pair(leftComma!!, this)

    val rightComma = PsiTreeUtil.skipWhitespacesAndCommentsForward(this)

    if (rightComma.elementType == PhpTokenTypes.opCOMMA)
        return Pair(this, rightComma!!)

    return Pair(this, this)
}

fun PsiElement.getNextTreePsiSibling(): PsiElement? {
    var element: PsiElement? = this

    while (element != null) {
        with(PsiTreeUtil.skipWhitespacesAndCommentsForward(element)) {
            if (this != null)
                return this
        }

        element = element.parent
    }

    return null
}

fun PsiElement?.isScalar(): Boolean {
    if (this == null)
        return false

    if (this is ConstantReference ||
        this is StringLiteralExpression ||
        this is ArrayCreationExpression ||
        this is ClassConstantReference ||
        elementType == PhpElementTypes.NUMBER)
        return true

    if (this is UnaryExpression)
        with(this.firstPsiChild ?: return false) {
            if (elementType == PhpElementTypes.NUMBER)
                return true
        }

    return false
}

fun Pair<PsiElement, PsiElement>.delete(): Unit =
    first.parent.deleteChildRange(first, second)
