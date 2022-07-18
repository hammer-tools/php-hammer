package net.rentalhost.plugins.extensions.psi

import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType
import com.jetbrains.php.lang.lexer.PhpTokenTypes
import com.jetbrains.php.lang.parser.PhpElementTypes
import com.jetbrains.php.lang.psi.elements.*
import com.jetbrains.php.lang.psi.elements.impl.StatementImpl

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

fun PsiElement.isStrictlyStatement(): Boolean =
    this::class == StatementImpl::class

fun PsiElement?.unparenthesize(): PsiElement? =
    if (this is ParenthesizedExpression) this.argument.unparenthesize()
    else this

fun PsiElement.unwrapStatement(): PsiElement {
    return if (this is StatementImpl &&
               this::class == StatementImpl::class) (this.firstPsiChild ?: return this).unwrapStatement()
    else this
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
