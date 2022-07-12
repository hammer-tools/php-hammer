package net.rentalhost.plugins.extensions.psi

import com.jetbrains.php.PhpClassHierarchyUtils
import com.jetbrains.php.lang.psi.elements.PhpClassMember

fun PhpClassMember.isMemberOverridden(): Boolean {
    var isOverridden = false

    PhpClassHierarchyUtils.processSuperMembers(this) { _, _, _ ->
        isOverridden = true
        false
    }

    return isOverridden
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
