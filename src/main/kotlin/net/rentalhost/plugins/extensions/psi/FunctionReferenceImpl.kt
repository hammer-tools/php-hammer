package net.rentalhost.plugins.extensions.psi

import com.intellij.psi.PsiElement
import com.jetbrains.php.lang.psi.elements.impl.FunctionReferenceImpl
import com.jetbrains.php.lang.psi.elements.impl.UnaryExpressionImpl

fun FunctionReferenceImpl.getErrorControlOperator(): PsiElement? =
    with(this.parent) {
        return if (this is UnaryExpressionImpl &&
                   this.firstChild.text === "@") return this.firstChild
        else null
    }
