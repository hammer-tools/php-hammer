package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.util.xmlb.annotations.OptionTag
import com.jetbrains.php.config.PhpLanguageLevel
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.Function
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.extensions.psi.*
import net.rentalhost.plugins.services.FactoryService
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.OptionsPanelService
import net.rentalhost.plugins.services.ProblemsHolderService
import javax.swing.JComponent

class AnonymousFunctionStaticInspection: PhpInspection() {
    @OptionTag
    var includeShortFunctions: Boolean = true

    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        override fun visitPhpFunction(element: Function) {
            if (!element.isAnonymous() ||
                element.isStatic())
                return

            if (!includeShortFunctions &&
                element.isShortFunction())
                return

            for (elementScope in element.scopes()) {
                if (elementScope.accessVariables().find { it.variableName == "this" } != null)
                    return
            }

            ProblemsHolderService.registerProblem(
                problemsHolder,
                element.firstChild,
                "This anonymous function can be static.",
                LocalQuickFixService.SimpleInlineQuickFix("Make this function static") {
                    element.insertBefore(FactoryService.createStaticKeyword(problemsHolder.project))
                }
            )
        }
    }

    override fun createOptionsPanel(): JComponent {
        return OptionsPanelService.create { component: OptionsPanelService ->
            component.addCheckbox(
                "Include short functions", includeShortFunctions,
                "This option allows the inspection to check arrow functions (<code>fn()</code>), in addition to regular functions (<code>function()</code>)."
            ) { includeShortFunctions = it }
        }
    }

    override fun getMinimumSupportedLanguageLevel(): PhpLanguageLevel = PhpLanguageLevel.PHP540
}
