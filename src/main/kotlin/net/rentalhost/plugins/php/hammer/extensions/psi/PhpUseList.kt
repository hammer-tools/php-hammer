package net.rentalhost.plugins.php.hammer.extensions.psi

import com.intellij.psi.util.PsiTreeUtil
import com.jetbrains.php.lang.psi.elements.PhpUseList
import com.jetbrains.php.lang.psi.elements.impl.FunctionImpl
import com.jetbrains.php.lang.psi.elements.impl.PhpUseListImpl
import com.jetbrains.php.lang.psi.elements.impl.VariableImpl

fun PhpUseList.getVariables(): Collection<VariableImpl>? =
  if (this.context is FunctionImpl) PsiTreeUtil.findChildrenOfType(this, VariableImpl::class.java)
  else null

fun PhpUseList.deleteTrailingComma(): Unit? =
  PhpUseListImpl.getTrailingComma(this)?.delete()
