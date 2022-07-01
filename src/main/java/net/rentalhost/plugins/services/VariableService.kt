package net.rentalhost.plugins.services

import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.psi.util.PsiTreeUtil

object VariableService {
    fun isReference(element: PsiElement): Boolean {
        val elementPrev = PsiTreeUtil.skipWhitespacesAndCommentsBackward(element)

        return elementPrev is LeafPsiElement &&
               elementPrev.text == "&"
    }
}
