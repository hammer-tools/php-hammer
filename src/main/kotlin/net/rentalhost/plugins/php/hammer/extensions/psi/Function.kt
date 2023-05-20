package net.rentalhost.plugins.php.hammer.extensions.psi

import com.intellij.psi.util.PsiTreeUtil
import com.jetbrains.php.codeInsight.controlFlow.instructions.PhpAccessInstruction
import com.jetbrains.php.codeInsight.controlFlow.instructions.PhpAccessVariableInstruction
import com.jetbrains.php.codeInsight.controlFlow.instructions.PhpArrayAccessInstruction
import com.jetbrains.php.lang.lexer.PhpTokenTypes
import com.jetbrains.php.lang.psi.PhpPsiUtil
import com.jetbrains.php.lang.psi.elements.GroupStatement
import com.jetbrains.php.lang.psi.elements.impl.GroupStatementImpl
import com.jetbrains.php.lang.psi.elements.impl.MethodImpl
import com.jetbrains.php.lang.psi.elements.Function as PhpFunction

fun PhpFunction.isAbstractMethod(): Boolean =
    this is MethodImpl && this.isAbstract

fun PhpFunction.isStatic(): Boolean =
    node.findChildByType(PhpTokenTypes.kwSTATIC) != null

fun PhpFunction.isShortFunction(): Boolean =
    node.findChildByType(PhpTokenTypes.kwFN) != null

fun PhpFunction.isAnonymous(): Boolean =
    name == ""

fun PhpFunction.functionBody(): GroupStatementImpl? =
    PhpPsiUtil.getChildByCondition(this, GroupStatement.INSTANCEOF)

fun PhpFunction.scopes(): MutableList<PhpFunction> =
    mutableListOf(this).apply { addAll(PsiTreeUtil.findChildrenOfType(this@scopes, PhpFunction::class.java)) }

fun PhpFunction.accessVariables(): List<PhpAccessInstruction> =
    controlFlow.instructions
        .filterIsInstance(PhpAccessInstruction::class.java)
        .filter { it is PhpAccessVariableInstruction || it is PhpArrayAccessInstruction }
        .filter { it.variableName != null }

fun PhpFunction.accessMutableVariables(): List<PhpAccessInstruction> =
    accessVariables().filter {
        it is PhpArrayAccessInstruction ||
        it.access.isWrite ||
        it.access.isReadRef
    }
