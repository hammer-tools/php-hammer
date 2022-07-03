package net.rentalhost.plugins.services

import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.jetbrains.php.lang.psi.elements.impl.VariableImpl

object VariableService {
    fun getLeafReference(element: VariableImpl): LeafPsiElement? {
        return with(PsiTreeUtil.skipWhitespacesAndCommentsBackward(element)) {
            if (this is LeafPsiElement && this.text == "&") this
            else null
        }
    }

    fun isLeafReference(element: VariableImpl): Boolean =
        getLeafReference(element) != null
}
