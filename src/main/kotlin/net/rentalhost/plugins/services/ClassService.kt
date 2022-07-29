package net.rentalhost.plugins.services

import com.intellij.openapi.project.Project
import com.jetbrains.php.lang.psi.elements.PhpClass
import net.rentalhost.plugins.extensions.psi.searchScope

object ClassService {
    fun findFQN(namespacedClassname: String, project: Project): PhpClass? =
        InstanceService.classesIndex.get(namespacedClassname.substringAfterLast("\\").lowercase(), project, project.searchScope())
            .firstOrNull { it.fqn.lowercase() == namespacedClassname.lowercase() }
}
