package net.rentalhost.plugins.php.hammer.services

import com.intellij.psi.PsiElement
import net.rentalhost.plugins.php.hammer.extensions.psi.isStrictlyStatement
import net.rentalhost.plugins.php.hammer.extensions.psi.unparenthesize

enum class SafeDeleteOptions(val value: Int) {
  TREAT_AS_TRUE(1),
  TREAT_AS_FALSE(2)
}

class SafeDeleteService(private val element: PsiElement, private val options: SafeDeleteOptions? = null) {
  val reference: PsiElement = with(element.unparenthesize()!!) {
    when {
      parent.isStrictlyStatement() -> parent
      else -> element
    }
  }

  fun delete() {
    if (reference.isStrictlyStatement()) {
      reference.delete()

      return
    }

    element.replace(
      if (options == SafeDeleteOptions.TREAT_AS_TRUE) FactoryService.createConstantReference(element.project, "true")
      else FactoryService.createConstantReference(element.project, "false")
    )
  }
}
