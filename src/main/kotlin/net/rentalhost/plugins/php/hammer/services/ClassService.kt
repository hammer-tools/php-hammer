package net.rentalhost.plugins.php.hammer.services

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.text.StringUtil
import com.jetbrains.php.PhpIndex
import com.jetbrains.php.codeInsight.PhpCodeInsightUtil
import com.jetbrains.php.lang.PhpLangUtil
import com.jetbrains.php.lang.psi.PhpGroupUseElement
import com.jetbrains.php.lang.psi.PhpGroupUseElement.PhpUseKeyword
import com.jetbrains.php.lang.psi.PhpPsiElementFactory
import com.jetbrains.php.lang.psi.elements.ClassReference
import com.jetbrains.php.lang.psi.elements.PhpClass
import com.jetbrains.php.refactoring.move.member.PhpMoveMemberProcessor

object ClassService {
  fun findFQN(namespacedClassname: String, project: Project): PhpClass? =
    PhpIndex.getInstance(project).getClassesByFQN(namespacedClassname.lowercase()).firstOrNull()

  fun import(classReference: ClassReference?, allowAliasing: Boolean = false) {
    val classReferenceFQN = PhpLangUtil.toFQN(classReference?.text ?: return)

    // If an `use` declaration with the same name already exists, it prevents the creation of an alias and retains it with absolute class name.
    if (!allowAliasing) {
      PhpCodeInsightUtil.collectImports(PhpCodeInsightUtil.findScopeForUseOperator(classReference)!!).forEach {
        for (declaration in it.declarations) {
          if (!PhpLangUtil.equalsClassNames(declaration.fqn, classReferenceFQN) &&
            StringUtil.equals(PhpGroupUseElement.getKeyword(declaration, null), PhpUseKeyword.CLASS.value))
            return
        }
      }
    }

    val classQualifiedName = PhpMoveMemberProcessor.importClassAndGetName(
      classReference, emptyList(), classReferenceFQN
    )

    classReference.replace(
      PhpPsiElementFactory.createClassReference(classReference.project, classQualifiedName)
    )
  }
}
