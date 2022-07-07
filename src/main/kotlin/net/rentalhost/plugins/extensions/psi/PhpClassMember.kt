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

fun PhpClassMember.isDefinedByOwnClass(): Boolean =
    !isMemberOverridden()
