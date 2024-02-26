package net.rentalhost.plugins.php.hammer.extensions.psi

import com.jetbrains.php.lang.psi.elements.PhpTypedElement
import com.jetbrains.php.lang.psi.resolve.types.PhpType

fun PhpTypedElement.getTypes() =
    globalType.typesWithParametrisedParts.map { PhpType.removeParametrisedType(it.toString()) }
