package net.rentalhost.plugins.extensions.psi

import com.jetbrains.php.codeInsight.controlFlow.instructions.PhpAccessInstruction
import com.jetbrains.php.codeInsight.controlFlow.instructions.PhpAccessVariableInstruction
import com.jetbrains.php.codeInsight.controlFlow.instructions.PhpArrayAccessInstruction
import com.jetbrains.php.lang.psi.PhpPsiUtil
import com.jetbrains.php.lang.psi.elements.GroupStatement
import com.jetbrains.php.lang.psi.elements.impl.FunctionImpl
import com.jetbrains.php.lang.psi.elements.impl.GroupStatementImpl
import com.jetbrains.php.lang.psi.elements.impl.MethodImpl

fun FunctionImpl.isAbstractMethod(): Boolean =
    this is MethodImpl && this.isAbstract

fun FunctionImpl.isStatic(): Boolean =
    firstChild.text.lowercase() == "static"

fun FunctionImpl.isAnonymous(): Boolean =
    name == ""

fun FunctionImpl.functionBody(): GroupStatementImpl? =
    PhpPsiUtil.getChildByCondition(this, GroupStatement.INSTANCEOF)

fun FunctionImpl.accessVariables(): List<PhpAccessInstruction> =
    controlFlow.instructions
        .filterIsInstance(PhpAccessInstruction::class.java)
        .filter { it is PhpAccessVariableInstruction || it is PhpArrayAccessInstruction }
        .filter { it.variableName != null }

fun FunctionImpl.accessMutableVariables(): List<PhpAccessInstruction> =
    accessVariables().filter {
        it is PhpArrayAccessInstruction ||
        it.access.isWrite ||
        it.access.isReadRef
    }
