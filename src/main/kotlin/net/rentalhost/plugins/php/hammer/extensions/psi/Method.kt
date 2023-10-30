package net.rentalhost.plugins.php.hammer.extensions.psi

import com.jetbrains.php.lang.psi.elements.Method
import com.jetbrains.php.lang.psi.elements.PhpClass

// To indicate that a method is overridable, it needs to be found in a higher class and cannot be private.
fun Method.isOverridable(classBase: PhpClass?): Boolean =
  classBase?.superClass?.findMethodByName(name)?.modifier?.isPrivate == false
