package net.rentalhost.plugins.extensions.psi

import com.intellij.psi.PsiElement

fun PsiElement?.insertBeforeElse(addIt: PsiElement, orElse: Lazy<() -> PsiElement>): PsiElement =
    this?.parent?.addBefore(addIt, this) ?: orElse.value.invoke()

fun PsiElement.insertBefore(addIt: PsiElement): PsiElement =
    this.parent.addBefore(addIt, this)

fun Pair<PsiElement, PsiElement>.delete(): Unit =
    first.parent.deleteChildRange(first, second)
