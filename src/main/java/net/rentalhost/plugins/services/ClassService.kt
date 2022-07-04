package net.rentalhost.plugins.services

import com.jetbrains.php.PhpClassHierarchyUtils
import com.jetbrains.php.lang.psi.elements.PhpClassMember
import com.jetbrains.php.lang.psi.elements.impl.MethodImpl

object ClassService {
    private fun isClassMemberOverridden(classMember: PhpClassMember): Boolean {
        var isOverridden = false

        PhpClassHierarchyUtils.processSuperMembers(classMember) { _, _, _ ->
            isOverridden = true
            false
        }

        return isOverridden
    }

    fun isMethodDefinedByOwnClass(method: MethodImpl): Boolean {
        return !isClassMemberOverridden(method)
    }
}
