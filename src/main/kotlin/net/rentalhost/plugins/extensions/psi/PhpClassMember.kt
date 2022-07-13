package net.rentalhost.plugins.extensions.psi

import com.jetbrains.php.PhpClassHierarchyUtils
import com.jetbrains.php.lang.psi.elements.PhpClass
import com.jetbrains.php.lang.psi.elements.PhpClassMember

fun PhpClassMember.isMemberOverridden(): Boolean {
    var isOverridden = false

    PhpClassHierarchyUtils.processSuperMembers(this) { _, _, _ ->
        isOverridden = true
        false
    }

    return isOverridden
}

fun PhpClassMember.getMemberOverridden(): PhpClass? {
    var element: PhpClass? = null

    PhpClassHierarchyUtils.processSuperMembers(this) { _, _, phpClass ->
        element = phpClass
        false
    }

    return element
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
