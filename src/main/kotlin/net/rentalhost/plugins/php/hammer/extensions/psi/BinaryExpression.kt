package net.rentalhost.plugins.php.hammer.extensions.psi

import com.intellij.psi.tree.IElementType
import com.jetbrains.php.lang.lexer.PhpTokenTypes
import com.jetbrains.php.lang.psi.elements.BinaryExpression

fun BinaryExpression.isOperator(operator: IElementType): Boolean =
  operationType == operator

fun BinaryExpression.isOperatorAnd(): Boolean =
  isOperator(PhpTokenTypes.opAND)

fun BinaryExpression.isOperatorEqual(): Boolean =
  isOperator(PhpTokenTypes.opEQUAL)

fun BinaryExpression.isOperatorNotEqual(): Boolean =
  isOperator(PhpTokenTypes.opNOT_EQUAL)
