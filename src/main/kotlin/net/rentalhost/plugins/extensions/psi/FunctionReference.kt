package net.rentalhost.plugins.extensions.psi

import com.intellij.psi.PsiElement
import com.jetbrains.php.lang.psi.elements.FunctionReference
import com.jetbrains.php.lang.psi.elements.impl.UnaryExpressionImpl

fun FunctionReference.getErrorControlOperator(): PsiElement? =
    with(this.parent) {
        return if (this is UnaryExpressionImpl &&
                   this.firstChild.text === "@") return this.firstChild
        else null
    }
