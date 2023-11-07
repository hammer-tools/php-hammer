package net.rentalhost.plugins.php.hammer.extensions.psi

import com.jetbrains.php.lang.psi.PhpPsiElementFactory
import com.jetbrains.php.lang.psi.elements.Parameter
import com.jetbrains.php.lang.psi.elements.impl.PhpPromotedFieldParameterImpl

fun PhpPromotedFieldParameterImpl.asParameter(): Parameter =
  PhpPsiElementFactory.createPhpPsiFromText(project, Parameter::class.java, "function A(${text.substring(typeDeclaration?.startOffsetInParent ?: 0)}){}")
