package net.rentalhost.plugins.php.hammer.inspections.codeWarning

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.codeInspection.options.OptCheckbox
import com.intellij.codeInspection.options.OptPane
import com.intellij.codeInspection.options.PlainMessage
import com.intellij.openapi.util.text.HtmlChunk
import com.intellij.psi.SmartPointerManager
import com.intellij.util.xmlb.annotations.OptionTag
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.Function
import com.jetbrains.php.lang.psi.elements.FunctionReference
import com.jetbrains.php.lang.psi.elements.MethodReference
import com.jetbrains.php.lang.psi.elements.impl.StatementImpl
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.extensions.psi.isBlade
import net.rentalhost.plugins.php.hammer.extensions.psi.isExactly
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService

class DebugFunctionUsageInspection : PhpInspection() {
  @OptionTag
  private var xdebugEnabled = true

  @OptionTag
  private var frameworksEnabled = true

  private val nativeRegex = Regex("^\\\\debug_", RegexOption.IGNORE_CASE)
  private val nativeFunctions = listOf(
    "\\var_dump",
    "\\var_export",
    "\\print_r",
    "\\get_defined_vars",
    "\\phpinfo",
    "\\error_log"
  )

  private val bladeSkip = listOf(
    "\\print_r",
    "\\get_defined_vars",
  )

  private val xdebugRegex = Regex("^\\\\xdebug_", RegexOption.IGNORE_CASE)

  private val frameworksRegex by lazy { Regex(frameworks.joinToString("|") { it.second.pattern }, RegexOption.IGNORE_CASE) }
  private val frameworks = listOf(
    Pair("Laravel", Regex("^\\\\(dd|dump)$")),
    Pair("Laravel", Regex("^\\\\Illuminate\\\\[^\\\\]+\\\\(dd|dump)$")),
  )

  override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object : PhpElementVisitor() {
    override fun visitPhpFunctionCall(function: FunctionReference) {
      if (function.parameterList == null)
        return

      val functionResolve = try {
        function.resolve()
      }
      catch (_: StringIndexOutOfBoundsException) {
        return
      }

      val functionName =
        if (functionResolve is Function) functionResolve.fqn
        else function.fqn.toString()

      if (function.containingFile.isBlade() && bladeSkip.contains(functionName))
        return

      val isNativeFunction by lazy { nativeFunctions.contains(functionName) || nativeRegex.containsMatchIn(functionName) }
      val isXdebugFunction by lazy { xdebugEnabled && xdebugRegex.containsMatchIn(functionName) }
      val isFrameworksFunction by lazy { frameworksEnabled && frameworksRegex.containsMatchIn(functionName) }

      if (!isNativeFunction &&
        !isXdebugFunction &&
        !isFrameworksFunction)
        return

      ProblemsHolderService.instance.registerProblem(
        problemsHolder, function, "debug function usage",
        if (function.parent.isExactly<StatementImpl>()) QuickFixService.instance.simpleDelete(
          "Drop debug function", SmartPointerManager.createPointer(function.parent)
        )
        else null
      )
    }

    override fun visitPhpMethodReference(method: MethodReference) = visitPhpFunctionCall(method)
  }

  override fun getOptionsPane(): OptPane {
    return OptPane.pane(
      OptCheckbox(
        "xdebugEnabled",
        PlainMessage("Include xdebug functions"),
        emptyList(),
        HtmlChunk.raw(
          "This option will include the functions related to the <code>xdebug</code> extension."
        )
      ),

      OptCheckbox(
        "frameworksEnabled",
        PlainMessage("Include frameworks functions"),
        emptyList(),
        HtmlChunk.raw(
          "This option includes functions related to PHP frameworks and other tools. " +
            "Currently supported are: ${frameworks.map { it.first }.distinct().sorted().joinToString(", ")} and others."
        )
      )
    )
  }
}
