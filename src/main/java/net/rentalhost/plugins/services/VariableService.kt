package net.rentalhost.plugins.services

import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.jetbrains.php.lang.psi.elements.impl.VariableImpl

object VariableService {
    fun getLeafReference(element: PsiElement): LeafPsiElement? {
        if (element !is VariableImpl)
            return null

        return with(PsiTreeUtil.skipWhitespacesAndCommentsBackward(element)) {
            if (this is LeafPsiElement && this.text == "&") this
            else null
        }
    }

    fun isLeafReference(element: PsiElement): Boolean =
        getLeafReference(element) != null
}
