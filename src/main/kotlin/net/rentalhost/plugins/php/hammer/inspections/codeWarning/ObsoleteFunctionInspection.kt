package net.rentalhost.plugins.php.hammer.inspections.codeWarning

import com.intellij.codeInspection.ProblemsHolder
import com.jetbrains.php.config.PhpLanguageLevel
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.FunctionReference
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService

class ObsoleteFunctionInspection : PhpInspection() {
  private val obsoleteReplacements = mapOf(
    Pair("\\rand", "random_int"),
    Pair("\\mt_rand", "random_int"),
  )

  override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object : PhpElementVisitor() {
    override fun visitPhpFunctionCall(function: FunctionReference) {
      val functionName = (function.fqn ?: return).lowercase()
      val functionTarget = obsoleteReplacements[functionName] ?: return
      val functionIdentifier = (function.nameNode ?: return).psi

      ProblemsHolderService.instance.registerProblem(
        problemsHolder,
        function,
        function.firstChild,
        functionIdentifier,
        "this function is obsolete, use $functionTarget() instead"
      )
    }
  }

  override fun getMinimumSupportedLanguageLevel(): PhpLanguageLevel = PhpLanguageLevel.PHP700
}
