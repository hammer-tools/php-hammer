package net.rentalhost.plugins.php.hammer.services

import com.intellij.openapi.project.Project
import com.jetbrains.php.PhpIndex
import com.jetbrains.php.lang.psi.elements.PhpClass

object ClassService {
  fun findFQN(namespacedClassname: String, project: Project): PhpClass? =
    PhpIndex.getInstance(project).getClassesByFQN(namespacedClassname.lowercase()).firstOrNull()
}
