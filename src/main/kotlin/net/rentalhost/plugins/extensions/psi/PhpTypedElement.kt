package net.rentalhost.plugins.extensions.psi

import com.jetbrains.php.lang.psi.elements.PhpTypedElement

fun PhpTypedElement.getTypes(): List<String> =
    type.types.map { it.toString() }
