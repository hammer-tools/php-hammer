package net.rentalhost.plugins.php.hammer.inspections.codeSmell

import com.intellij.codeInspection.ProblemsHolder
import com.jetbrains.php.config.PhpLanguageLevel
import com.jetbrains.php.lang.PhpLangUtil
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.*
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.hammer.extensions.psi.isName
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService

class FrameworkOptionalReplacementInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        override fun visitPhpFunctionCall(element: FunctionReference) {
            if (!element.isName("optional") ||
                element.parameters.size != 1)
                return

            val elementParent = element.parent

            if (elementParent !is FieldReference &&
                elementParent !is MethodReference)
                return

            val parameterType = element.parameters.first()

            if (parameterType !is Variable &&
                parameterType !is FieldReference &&
                parameterType !is MethodReference &&
                parameterType !is FunctionReference &&
                parameterType !is ClassConstantReference &&
                parameterType !is ConstantReference)
                return

            if (PhpLangUtil.isNull(parameterType) ||
                PhpLangUtil.isTrue(parameterType) ||
                PhpLangUtil.isFalse(parameterType))
                return

            ProblemsHolderService.instance.registerProblem(
                problemsHolder,
                element,
                "optional() can be replaced by null safe operator"
            )
        }
    }

    override fun getMinimumSupportedLanguageLevel(): PhpLanguageLevel =
        PhpLanguageLevel.PHP800
}
