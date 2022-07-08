package net.rentalhost.plugins.extensions.psi

import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType
import com.jetbrains.php.lang.parser.PhpElementTypes
import com.jetbrains.php.lang.psi.elements.ArrayCreationExpression
import com.jetbrains.php.lang.psi.elements.ConstantReference
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression

fun PsiElement?.insertBeforeElse(addIt: PsiElement, orElse: Lazy<() -> PsiElement>): PsiElement =
    this?.parent?.addBefore(addIt, this) ?: orElse.value.invoke()

fun PsiElement.insertBefore(addIt: PsiElement): PsiElement =
    this.parent.addBefore(addIt, this)

fun PsiElement?.isScalar(): Boolean {
    if (this == null)
        return false

    if (this is ConstantReference ||
        this is StringLiteralExpression ||
        this is ArrayCreationExpression ||
        elementType == PhpElementTypes.NUMBER)
        return true

    return false
}

fun Pair<PsiElement, PsiElement>.delete(): Unit =
    first.parent.deleteChildRange(first, second)
