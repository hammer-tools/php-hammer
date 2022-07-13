package net.rentalhost.plugins.extensions.psi

import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType
import com.jetbrains.php.lang.parser.PhpElementTypes
import com.jetbrains.php.lang.psi.elements.*

fun PsiElement?.insertBeforeElse(addIt: PsiElement, orElse: Lazy<() -> PsiElement>): PsiElement =
    this?.parent?.addBefore(addIt, this) ?: orElse.value.invoke()

fun PsiElement.insertBefore(addIt: PsiElement): PsiElement =
    this.parent.addBefore(addIt, this)

fun PsiElement.swap(swapWith: PsiElement): Unit = with(copy()) {
    this@swap.replace(swapWith)
    swapWith.replace(this)
}

fun PsiElement.isVariadicPreceded(): Boolean =
    with(PsiTreeUtil.skipWhitespacesAndCommentsBackward(this)) {
        return this is LeafPsiElement &&
               this.text == "..."
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
