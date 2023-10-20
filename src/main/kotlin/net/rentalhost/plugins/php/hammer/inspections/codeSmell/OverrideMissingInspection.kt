package net.rentalhost.plugins.php.hammer.inspections.codeSmell

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.refactoring.suggested.createSmartPointer
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.Method
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.extensions.psi.addAttribute
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService

class OverrideMissingInspection : PhpInspection() {
  override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object : PhpElementVisitor() {
    override fun visitPhpMethod(method: Method) {
      // Considers only methods that can be found in parent classes.
      // This will exclude methods declared in traits, which can be a problem.
      if (method.containingClass?.superClass?.findMethodByName(method.name) == null)
        return

      // If the #[\Override] attribute is found, then everything is fine here.
      for (attribute in method.attributes) {
        if (attribute.fqn == "\\Override")
          return
      }

      val methodPointer = method.createSmartPointer()

      // Otherwise, we have found a problem compatible with this inspection.
      ProblemsHolderService.instance.registerProblem(
        problemsHolder,
        method.nameIdentifier ?: method,
        "this method performs an override; consider using the #[Override] attribute",
        QuickFixService.instance.simpleInline("Declare #[Override] attribute") {
          methodPointer.element?.addAttribute("Override")
        }
      )
    }
  }
}
