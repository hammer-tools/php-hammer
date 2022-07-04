package net.rentalhost.plugins.extensions

import com.jetbrains.php.lang.psi.elements.impl.FunctionImpl
import com.jetbrains.php.lang.psi.elements.impl.MethodImpl

fun FunctionImpl.isAbstractMethod(): Boolean =
    this is MethodImpl &&
    this.isAbstract
