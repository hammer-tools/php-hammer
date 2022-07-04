package net.rentalhost.plugins.extensions.psi

import com.intellij.psi.util.PsiTreeUtil
import com.jetbrains.php.lang.psi.elements.impl.FunctionImpl
import com.jetbrains.php.lang.psi.elements.impl.PhpUseListImpl
import com.jetbrains.php.lang.psi.elements.impl.VariableImpl

fun PhpUseListImpl.getVariables(): Collection<VariableImpl>? =
    if (this.context is FunctionImpl) PsiTreeUtil.findChildrenOfType(this, VariableImpl::class.java)
    else null
