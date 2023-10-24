package net.rentalhost.plugins.php.hammer.inspections.codeSmell

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.findParentOfType
import com.intellij.refactoring.suggested.createSmartPointer
import com.intellij.util.xmlb.annotations.OptionTag
import com.jetbrains.php.PhpIndex
import com.jetbrains.php.config.PhpLanguageLevel
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.Method
import com.jetbrains.php.lang.psi.elements.MethodReference
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.extensions.psi.addAttribute
import net.rentalhost.plugins.php.hammer.extensions.psi.functionBody
import net.rentalhost.plugins.php.hammer.services.LanguageService
import net.rentalhost.plugins.php.hammer.services.OptionsPanelService
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService
import javax.swing.JComponent

class OverrideMissingInspection : PhpInspection() {
  @OptionTag
  var considerParentCallReplacement = false

  @OptionTag
  var supportTraits = true

  @OptionTag
  var supportOlderVersions = false

  override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object : PhpElementVisitor() {
    override fun visitPhpMethod(method: Method) {
      if (!supportOlderVersions && !LanguageService.atLeast(problemsHolder.project, PhpLanguageLevel.PHP830))
        return

      val methodClass = method.containingClass

      // If this method is from a trait, it checks the connections to it.
      if (supportTraits && methodClass?.isTrait == true) {
        // If this method is connected to any `use` that doesn't define this method itself, then it cannot be an overridden.
        PhpIndex.getInstance(method.project).getTraitUsages(methodClass).forEach {
          if (it.superClass?.findMethodByName(method.name) == null)
            return
        }
      }
      // Considers only methods that can be found in parent classes.
      // This will exclude methods declared in traits, which is expected here.
      else if (methodClass?.superClass?.findMethodByName(method.name) == null)
        return

      // If the #[\Override] attribute is found, then everything is fine here.
      for (attribute in method.attributes) {
        if (attribute.fqn == "\\Override")
          return
      }

      if (considerParentCallReplacement) {
        PsiTreeUtil.findChildrenOfType(method.functionBody(), MethodReference::class.java).forEach {
          if (it.classReference?.name?.lowercase() == "parent" &&
            it.name?.lowercase() == method.name.lowercase() &&
            it.findParentOfType<Method>() == method)
            return
        }
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

  override fun createOptionsPanel(): JComponent {
    return OptionsPanelService.create { component: OptionsPanelService ->
      component.addCheckbox(
        "Consider parent::call() as a replacement", considerParentCallReplacement,
        "When this option is enabled, you can omit <code>#[Override]</code> if you perform a <code>parent::call()</code> for the suggested method. " +
          "In such cases, <code>#[Override]</code> becomes redundant for the code."
      ) { considerParentCallReplacement = it }

      component.addCheckbox(
        "Support traits", supportTraits,
        "Allows this inspection to identify methods declared in traits as well."
      ) { supportTraits = it }

      component.addCheckbox(
        "Support for older versions", supportOlderVersions,
        "Allow this inspection when the PHP version is less than 8.3. " +
          "The feature itself will be inoperative, but it prepares for a code update in the future."
      ) { supportOlderVersions = it }
    }
  }

  override fun getMinimumSupportedLanguageLevel(): PhpLanguageLevel = PhpLanguageLevel.PHP800
}
