package net.rentalhost.plugins.php.hammer.inspections.codeError

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.util.parentOfTypes
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.Function
import com.jetbrains.php.lang.psi.elements.Variable
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.extensions.psi.isAnonymous
import net.rentalhost.plugins.php.hammer.extensions.psi.isStatic
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService

class StaticAnonymousFunctionCannotAccessThisInspection : PhpInspection() {
  override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object : PhpElementVisitor() {
    override fun visitPhpVariable(variable: Variable?) {
      if (variable?.name?.lowercase() != "this") return

      val functionScope = variable.parentOfTypes(Function::class) ?: return

      if (!functionScope.isAnonymous() || !functionScope.isStatic()) return

      ProblemsHolderService.instance.registerProblem(
        problemsHolder,
        functionScope.firstChild,
        "static anonymous functions cannot access \$this",
        QuickFixService.instance.simpleDelete("Delete this \"static\" declaration")
      )
    }
  }
}
