package net.rentalhost.plugins.php.hammer.inspections.codeWarning

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.SmartPointerManager
import com.intellij.util.xmlb.annotations.OptionTag
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.Function
import com.jetbrains.php.lang.psi.elements.FunctionReference
import com.jetbrains.php.lang.psi.elements.MethodReference
import com.jetbrains.php.lang.psi.elements.impl.StatementImpl
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.extensions.psi.isBlade
import net.rentalhost.plugins.extensions.psi.isExactly
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.OptionsPanelService
import net.rentalhost.plugins.services.ProblemsHolderService
import javax.swing.JComponent

class DebugFunctionUsageInspection: PhpInspection() {
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

    private val xdebugRegex = Regex("^\\\\xdebug_", RegexOption.IGNORE_CASE)

    private val frameworksRegex by lazy { Regex(frameworks.joinToString("|") { it.second.pattern }, RegexOption.IGNORE_CASE) }
    private val frameworks = listOf(
        Pair("Laravel", Regex("^\\\\(dd|dump)$")),
        Pair("Laravel", Regex("^\\\\Illuminate\\\\[^\\\\]+\\\\(dd|dump)$")),
    )

    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        override fun visitPhpFunctionCall(function: FunctionReference) {
            if (function.containingFile.isBlade())
                return

            if (function.parameterList == null)
                return

            val functionResolve = function.resolve()

            val functionName =
                if (functionResolve is Function) functionResolve.fqn
                else function.fqn.toString()

            val isNativeFunction by lazy { nativeFunctions.contains(functionName) || nativeRegex.containsMatchIn(functionName) }
            val isXdebugFunction by lazy { xdebugEnabled && xdebugRegex.containsMatchIn(functionName) }
            val isFrameworksFunction by lazy { frameworksEnabled && frameworksRegex.containsMatchIn(functionName) }

            if (!isNativeFunction &&
                !isXdebugFunction &&
                !isFrameworksFunction)
                return

            ProblemsHolderService.registerProblem(
                problemsHolder, function, "debug function usage",
                if (function.parent.isExactly<StatementImpl>()) LocalQuickFixService.SimpleDeleteQuickFix(
                    "Drop debug function", SmartPointerManager.createPointer(function.parent)
                )
                else null
            )
        }

        override fun visitPhpMethodReference(method: MethodReference) = visitPhpFunctionCall(method)
    }

    override fun createOptionsPanel(): JComponent {
        return OptionsPanelService.create { component: OptionsPanelService ->
            component.addCheckbox(
                "Include xdebug functions", xdebugEnabled,
                "This option will include the functions related to the <code>xdebug</code> extension."
            ) { xdebugEnabled = it }

            component.addCheckbox(
                "Include frameworks functions", frameworksEnabled,
                "This option includes functions related to PHP frameworks and other tools. " +
                "Currently supported are: ${frameworks.map { it.first }.distinct().sorted().joinToString(", ")} and others."
            ) { frameworksEnabled = it }
        }
    }
}
