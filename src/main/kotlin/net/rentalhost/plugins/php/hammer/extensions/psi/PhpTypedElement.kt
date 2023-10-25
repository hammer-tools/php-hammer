package net.rentalhost.plugins.php.hammer.extensions.psi

import com.jetbrains.php.lang.psi.elements.PhpTypedElement

fun PhpTypedElement.getTypes() =
  globalType.types.map { it.toString() }
