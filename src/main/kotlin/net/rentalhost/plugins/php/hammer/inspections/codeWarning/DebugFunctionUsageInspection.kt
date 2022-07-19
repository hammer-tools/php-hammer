package net.rentalhost.plugins.php.hammer.inspections.codeWarning

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.util.xmlb.annotations.OptionTag
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.FunctionReference
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.services.OptionsPanelService
import net.rentalhost.plugins.services.ProblemsHolderService
import javax.swing.JComponent

class DebugFunctionUsageInspection: PhpInspection() {
    @OptionTag
    private var xdebugEnabled = true

    private val nativeFunctions = listOf("\\var_dump", "\\print_r", "\\get_defined_vars")
    private val nativeRegex = Regex("^\\\\debug_", RegexOption.IGNORE_CASE)

    private val xdebugRegex = Regex("^\\\\xdebug_", RegexOption.IGNORE_CASE)

    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        override fun visitPhpFunctionCall(function: FunctionReference) {
            val functionName = function.fqn.toString()

            val isNativeFunction by lazy { nativeFunctions.contains(functionName) || nativeRegex.containsMatchIn(functionName) }
            val isXdebugFunction by lazy { xdebugEnabled && xdebugRegex.containsMatchIn(functionName) }

            if (!isNativeFunction &&
                !isXdebugFunction)
                return

            ProblemsHolderService.registerProblem(problemsHolder, function, "Debug-related function usage.")
        }
    }

    override fun createOptionsPanel(): JComponent {
        return OptionsPanelService.create { component: OptionsPanelService ->
            component.addCheckbox(
                "Include xdebug functions", xdebugEnabled,
                "This option will include the functions related to the <code>xdebug</code> extension."
            ) { xdebugEnabled = it }
        }
    }
}
