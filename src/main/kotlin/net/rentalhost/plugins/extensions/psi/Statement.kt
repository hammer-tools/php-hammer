package net.rentalhost.plugins.extensions.psi

import com.jetbrains.php.lang.psi.elements.PhpExit
import com.jetbrains.php.lang.psi.elements.PhpReturn
import com.jetbrains.php.lang.psi.elements.Statement
import com.jetbrains.php.lang.psi.elements.impl.PhpThrowImpl

fun Statement.isTerminatingStatement(): Boolean = with(children) {
    size == 1 && with(get(0)) {
        this is PhpReturn ||
        this is PhpThrowImpl ||
        this is PhpExit ||
        (this is Statement && isTerminatingStatement())
    }
}
