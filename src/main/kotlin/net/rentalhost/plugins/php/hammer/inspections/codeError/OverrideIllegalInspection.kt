package net.rentalhost.plugins.php.hammer.inspections.codeError

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.codeInspection.options.OptCheckbox
import com.intellij.codeInspection.options.OptPane
import com.intellij.codeInspection.options.PlainMessage
import com.intellij.openapi.util.text.HtmlChunk
import com.intellij.util.xmlb.annotations.OptionTag
import com.jetbrains.php.PhpIndex
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.inspections.attributes.PhpRemoveAttributeQuickFix
import com.jetbrains.php.lang.psi.elements.Method
import com.jetbrains.php.lang.psi.elements.PhpAttribute
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.extensions.psi.isOverridable
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService

class OverrideIllegalInspection : PhpInspection() {
  @OptionTag
  var considerUnusedTraits = false

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
          if (!considerUnusedTraits && isEmpty())
            return

          if (isNotEmpty() && all { method.isOverridable(it) })
            return
        }
      }
      // Considers only methods that do not perform overrides.
      else if (method.isOverridable(methodClass)) {
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

  override fun getOptionsPane(): OptPane {
    return OptPane.pane(
      OptCheckbox(
        "considerUnusedTraits",
        PlainMessage("Consider unused traits"),
        emptyList(),
        HtmlChunk.raw(
          "When this option is enabled, <code>trait</code> that haven't been used (via the <code>use</code> keyword) " +
            "in any class will not trigger issues until they are actually used somewhere."
        )
      ),
    )
  }
}
