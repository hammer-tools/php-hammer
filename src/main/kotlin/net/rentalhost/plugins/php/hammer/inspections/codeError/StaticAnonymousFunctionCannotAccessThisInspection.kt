package net.rentalhost.plugins.php.hammer.inspections.codeError

import com.intellij.codeInspection.ProblemsHolder
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.Function
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.extensions.psi.*
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService

class StaticAnonymousFunctionCannotAccessThisInspection : PhpInspection() {
  override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object : PhpElementVisitor() {
    override fun visitPhpFunction(element: Function) {
      if (!element.isAnonymous() ||
        !element.isStatic())
        return

      for (elementScope in element.scopes()) {
        if (elementScope.accessVariables().find { it.variableName == "this" } == null)
          continue

        ProblemsHolderService.instance.registerProblem(
          problemsHolder,
          element.firstChild,
          "static anonymous functions cannot access \$this",
          QuickFixService.instance.simpleDelete("Delete this \"static\" declaration")
        )

        return
      }
    }
  }
}
