package net.rentalhost.plugins.php.hammer.extensions.psi

import com.jetbrains.php.PhpClassHierarchyUtils
import com.jetbrains.php.lang.psi.elements.PhpClass
import com.jetbrains.php.lang.psi.elements.PhpClassMember

private fun containsName(baseClass: PhpClass, name: String, subject: (PhpClass) -> Array<String>): Boolean {
    var found = false

    PhpClassHierarchyUtils.processSupers(baseClass, true, true) { phpClass ->
        subject.invoke(phpClass).forEach { subjectName ->
            if (subjectName == name) {
                found = true
                return@processSupers false
            }
        }

        return@processSupers true
    }

    return found
}

fun PhpClass.hasInterface(name: String): Boolean =
    containsName(this, name) { phpClass -> phpClass.interfaceNames }

fun PhpClassMember.isMemberOverridden(): Boolean {
    var isOverridden = false

    PhpClassHierarchyUtils.processSuperMembers(this) { _, _, _ ->
        isOverridden = true
        false
    }

    return isOverridden
}

fun PhpClassMember.getMemberOverridden(): PhpClass? {
    var classBase = containingClass

    while (classBase != null) {
        classBase = classBase.superClass

        if (classBase?.findMethodByName(name) != null)
            return classBase
    }

    return null
}

fun PhpClassMember.isMemberOverrided(): Boolean {
    var isOverrided = false

    PhpClassHierarchyUtils.processOverridingMembers(this) { _, _, _ ->
        isOverrided = true
        false
    }

    return isOverrided
}

fun PhpClassMember.isDefinedByOwnClass(): Boolean =
    !isMemberOverridden()
