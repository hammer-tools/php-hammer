package net.rentalhost.plugins.extensions.psi

import com.jetbrains.php.codeInsight.controlFlow.instructions.impl.PhpAccessVariableInstructionImpl
import com.jetbrains.php.lang.psi.PhpPsiUtil
import com.jetbrains.php.lang.psi.elements.GroupStatement
import com.jetbrains.php.lang.psi.elements.impl.FunctionImpl
import com.jetbrains.php.lang.psi.elements.impl.GroupStatementImpl
import com.jetbrains.php.lang.psi.elements.impl.MethodImpl

fun FunctionImpl.isAbstractMethod(): Boolean =
    this is MethodImpl &&
    this.isAbstract

fun FunctionImpl.functionBody(): GroupStatementImpl? =
    PhpPsiUtil.getChildByCondition(this, GroupStatement.INSTANCEOF)

fun FunctionImpl.accessVariables(): List<PhpAccessVariableInstructionImpl> =
    this.controlFlow.instructions
        .filterIsInstance<PhpAccessVariableInstructionImpl>()
