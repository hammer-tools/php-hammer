package net.rentalhost.plugins.php.hammer.inspections.codeWarning

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.codeInspection.options.OptCheckbox
import com.intellij.codeInspection.options.OptPane
import com.intellij.codeInspection.options.PlainMessage
import com.intellij.openapi.util.text.HtmlChunk
import com.intellij.psi.createSmartPointer
import com.intellij.util.xmlb.annotations.OptionTag
import com.jetbrains.php.codeInsight.controlFlow.instructions.impl.PhpCallInstructionImpl
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.GroupStatement
import com.jetbrains.php.lang.psi.elements.Method
import com.jetbrains.php.lang.psi.elements.PhpModifier
import com.jetbrains.php.lang.psi.elements.PhpReturn
import com.jetbrains.php.lang.psi.elements.impl.MethodImpl
import com.jetbrains.php.lang.psi.elements.impl.MethodReferenceImpl
import com.jetbrains.php.lang.psi.elements.impl.PhpThrowImpl
import com.jetbrains.php.lang.psi.elements.impl.PhpTraitUseRuleImpl
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.extensions.psi.*
import net.rentalhost.plugins.php.hammer.services.FactoryService
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService

class MissingParentCallInspection : PhpInspection() {
    @OptionTag
    var checkOverrideAttribute = true

    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object : PhpElementVisitor() {
        override fun visitPhpMethod(method: Method) {
            if (method.isAbstract) return
            if ((method.containingClass ?: return).isTrait) return

            if (!checkOverrideAttribute && method.attributes.any { it.fqn == "\\Override" })
                return

            val baseClass = method.getMemberOverridden() ?: return

            if (method.containingClass?.superClass?.findMethodByName(method.name) == null)
                return

            val baseMethod = baseClass.findMethodByName(method.name)
            val baseMethodOriginal =
                if (baseMethod is PhpTraitUseRuleImpl.NonAbsoluteTraitUseRuleRenameableFakePsiElement) baseMethod.original ?: return
                else (baseMethod as? MethodImpl) ?: return

            if (baseMethodOriginal.isAbstractMethod()) return

            with((baseMethodOriginal.functionBody() ?: return).getSingleStatement()) {
                if (this is PhpReturn || this is PhpThrowImpl)
                    return
            }

            if (baseMethodOriginal.scope.controlFlow.instructions.size == 2) return

            val methodName = lazy { method.name.lowercase() }
            val methodCall = method.scope.controlFlow.instructions.find {
                it is PhpCallInstructionImpl &&
                        with(it.functionReference as? MethodReferenceImpl ?: return@find false) {
                            referenceType == PhpModifier.State.PARENT &&
                                    (name ?: return@find false).lowercase() == methodName.value
                        }
            }

            if (methodCall != null) return

            val baseMethodPointer = baseMethodOriginal.createSmartPointer()

            ProblemsHolderService.instance.registerProblem(
                problemsHolder,
                method.nameIdentifier ?: return,
                "missing parent::${method.name}() call",
                QuickFixService.instance.simpleInline("Add call to parent::${method.name}()") {
                    with(method.functionBody()) {
                        val parentCall = FactoryService.createFunctionCall(
                            method.project,
                            "parent::${method.name}",
                            (baseMethodPointer.element ?: return@simpleInline).parameters.mapIndexed { parameterIndex, parameter ->
                                if (method.getParameter(parameterIndex) != null) {
                                    if (parameter.isVariadic) "...$${parameter.name}"
                                    else "$${parameter.name}"
                                } else "null"
                            }
                        )

                        val parentCallElement = (this ?: return@with).firstPsiChild.insertBeforeElse(parentCall, lazy {
                            { replace(FactoryService.createFunctionBody(project, "${parentCall.text};")) }
                        })

                        if (parentCallElement !is GroupStatement) {
                            parentCallElement.insertAfter(FactoryService.createSemicolon(method.project))
                        }
                    }
                }
            )
        }
    }

    override fun getOptionsPane(): OptPane {
        return OptPane.pane(
            OptCheckbox(
                "checkOverrideAttribute",
                PlainMessage("Check even with the #[Override] attribute"),
                emptyList(),
                HtmlChunk.raw(
                    "When this option is enabled, the inspection will run even if the <code>#[Override]</code> attribute is applied to the method."
                )
            )
        )
    }
}
