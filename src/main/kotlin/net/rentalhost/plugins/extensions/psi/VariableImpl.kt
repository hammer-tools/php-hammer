package net.rentalhost.plugins.extensions.psi

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.refactoring.suggested.endOffset
import com.intellij.refactoring.suggested.startOffset
import com.jetbrains.php.lang.psi.elements.impl.VariableImpl

fun VariableImpl.getLeafRef(): LeafPsiElement? =
    with(PsiTreeUtil.skipWhitespacesAndCommentsBackward(element)) {
        if (this is LeafPsiElement && this.text == "&") this
        else null
    }

fun VariableImpl.isRef(): Boolean =
    this.getLeafRef() != null

fun VariableImpl.getLeafTrailingComma(): LeafPsiElement? =
    with(PsiTreeUtil.skipWhitespacesAndCommentsForward(element)) {
        if (this is LeafPsiElement && this.text == ",") this
        else null
    }

fun VariableImpl.declarationChildRange(includeTrailingComma: Boolean = false): Pair<PsiElement, PsiElement> =
    Pair(
        with(getLeafRef()) { this ?: this@declarationChildRange },
        if (includeTrailingComma) with(getLeafTrailingComma()) { this ?: this@declarationChildRange } else this
    )

fun VariableImpl.declarationTextRange(elementContext: PsiElement): TextRange =
    TextRange(
        (getLeafRef()?.startOffset ?: startOffset) - elementContext.startOffset,
        endOffset - elementContext.startOffset
    )

fun Collection<VariableImpl>.declarationTextRange(elementContext: PsiElement): TextRange =
    TextRange(
        first().declarationTextRange(elementContext).startOffset,
        last().endOffset - elementContext.startOffset
    )
