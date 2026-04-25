package net.rentalhost.plugins.php.hammer.extensions.psi

import com.jetbrains.php.PhpClassHierarchyUtils
import com.jetbrains.php.lang.psi.elements.Method
import com.jetbrains.php.lang.psi.elements.PhpClass
import com.jetbrains.php.lang.psi.elements.impl.MethodImpl

// To indicate that a method is overridable, it needs to be found in a higher class and cannot be private.
// It will exclude phpdoc methods, but will consider methods from implemented interfaces.
fun Method.isOverridable(classBase: PhpClass? = containingClass): Boolean {
    var classCurrent = classBase

    while (classCurrent != null) {
        classCurrent = classCurrent.superClass

        with(classCurrent?.findMethodByName(name)) {
            if (this is MethodImpl) {
                return !this.modifier.isPrivate
            }
        }
    }

    // Also checks methods from implemented interfaces
    var foundOnInterface = false

    if (classBase != null) {
        PhpClassHierarchyUtils.processSupers(classBase, false, true) { phpClass ->
            with(phpClass.findMethodByName(name)) {
                if (this is MethodImpl) {
                    foundOnInterface = true

                    return@processSupers false
                }
            }

            return@processSupers true
        }
    }

    return foundOnInterface
}
