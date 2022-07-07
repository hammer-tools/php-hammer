package net.rentalhost.plugins.extensions.psi

import com.jetbrains.php.codeInsight.controlFlow.instructions.PhpAccessInstruction
import com.jetbrains.php.codeInsight.controlFlow.instructions.PhpAccessVariableInstruction
import com.jetbrains.php.codeInsight.controlFlow.instructions.PhpArrayAccessInstruction

val PhpAccessInstruction.variableName: CharSequence?
    get() =
        when (this) {
            is PhpAccessVariableInstruction -> variableName
            is PhpArrayAccessInstruction -> variableName
            else -> null
        }

fun List<PhpAccessInstruction>.names(): List<CharSequence> =
    map { it.variableName!! }.distinct()
