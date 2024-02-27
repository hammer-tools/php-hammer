package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.codeInspection.options.OptCheckbox
import com.intellij.codeInspection.options.OptPane
import com.intellij.codeInspection.options.PlainMessage
import com.intellij.openapi.util.text.HtmlChunk
import com.intellij.refactoring.suggested.createSmartPointer
import com.intellij.util.xmlb.annotations.OptionTag
import com.jetbrains.php.config.PhpLanguageLevel
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.Function
import com.jetbrains.php.lang.psi.elements.Method
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.extensions.psi.*
import net.rentalhost.plugins.php.hammer.services.FactoryService
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService

class AnonymousFunctionStaticInspection : PhpInspection() {
    @OptionTag
    var includeShortFunctions: Boolean = true

    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object : PhpElementVisitor() {
        override fun visitPhpFunction(element: Function) {
            if (element.containingFile.isBlade())
                return

            if (!element.isAnonymous() || element.isStatic())
                return

            if (!includeShortFunctions && element.isShortFunction())
                return

            for (elementScope in element.scopes()) {
                if (elementScope.accessVariables().find { it.variableName == "this" } != null)
                    return

                for (flowInstruction in elementScope.controlFlow.instructions) {
                    val flowInstructionMethod = flowInstruction.anchor?.reference?.resolve()

                    if (flowInstructionMethod is Method && !flowInstructionMethod.isStatic)
                        return
                }
            }

            val elementPointer = element.firstChild.createSmartPointer()

            ProblemsHolderService.instance.registerProblem(
                problemsHolder,
                element.firstChild,
                "anonymous function can be static",
                QuickFixService.instance.simpleInline("Make this function static") {
                    with(elementPointer.element ?: return@simpleInline) {
                        insertBefore(FactoryService.createStaticKeyword(problemsHolder.project))
                    }
                }
            )
        }
    }

    override fun getOptionsPane(): OptPane {
        return OptPane.pane(
            OptCheckbox(
                "includeShortFunctions",
                PlainMessage("Include short functions"),
                emptyList(),
                HtmlChunk.raw(
                    "This option allows the inspection to check arrow functions (<code>fn()</code>), " +
                            "in addition to regular functions (<code>function()</code>)."
                )
            )
        )
    }

    override fun getMinimumSupportedLanguageLevel(): PhpLanguageLevel = PhpLanguageLevel.PHP540
}
