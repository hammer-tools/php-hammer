package net.rentalhost.plugins.php.hammer.inspections.codeError

import com.intellij.codeInspection.ProblemsHolder
import com.jetbrains.php.PhpIndex
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.inspections.attributes.PhpRemoveAttributeQuickFix
import com.jetbrains.php.lang.psi.elements.Method
import com.jetbrains.php.lang.psi.elements.PhpAttribute
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService

class OverrideIllegalInspection : PhpInspection() {
  override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object : PhpElementVisitor() {
    override fun visitPhpAttribute(attribute: PhpAttribute) {
      if (attribute.fqn != "\\Override")
        return

      val method = attribute.owner as? Method ?: return
      val methodClass = method.containingClass ?: return

      if (methodClass.isTrait) {
        // Traits should be considered here as well.
        // But to be considered an override, it needs to be an override for all methods that use the trait.
        with(PhpIndex.getInstance(method.project).getTraitUsages(methodClass)) {
          if (isNotEmpty() && all { it.superClass?.findMethodByName(method.name) != null })
            return
        }
      }
      else if (methodClass.superClass?.findMethodByName(method.name) != null) {
        // Considers only methods that do not perform overrides.
        return
      }

      // Otherwise, we have found a problem compatible with this inspection.
      ProblemsHolderService.instance.registerProblem(
        problemsHolder,
        attribute,
        "this method doesn't actually perform an override; remove this illegal #[Override] attribute",
        QuickFixService.instance.simpleInline("Remove illegal attribute") {
          PhpRemoveAttributeQuickFix.removeAttribute(attribute)
        }
      )
    }
  }
}
