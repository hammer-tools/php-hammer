package net.rentalhost.plugins.php.hammer.extensions.psi

import com.jetbrains.php.lang.psi.elements.MethodReference

fun MethodReference.isName(expectedName: String): Boolean {
  return name.equals(expectedName, true)
}
