package net.rentalhost.plugins.php.hammer.inspections.codeSmell

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.refactoring.suggested.createSmartPointer
import com.jetbrains.php.config.PhpLanguageLevel
import com.jetbrains.php.lang.PhpLangUtil
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.lexer.PhpTokenTypes
import com.jetbrains.php.lang.psi.PhpPsiUtil
import com.jetbrains.php.lang.psi.elements.*
import com.jetbrains.php.lang.psi.elements.impl.MemberReferenceImpl
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.extensions.psi.insertBefore
import net.rentalhost.plugins.php.hammer.extensions.psi.isName
import net.rentalhost.plugins.php.hammer.services.FactoryService
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService

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

            val elementPointer = element.createSmartPointer()
            val elementParentPointer = elementParent.createSmartPointer()
            val elementParameterPointer = parameterType.createSmartPointer()

            ProblemsHolderService.instance.registerProblem(
                problemsHolder,
                element,
                "optional() can be replaced by null safe operator",
                QuickFixService.instance.simpleInline("Replace with null-safe operator") {
                    elementPointer.element?.replace(elementParameterPointer.element ?: return@simpleInline)

                    val elementParentLocal = (elementParentPointer.element ?: return@simpleInline) as? MemberReferenceImpl ?: return@simpleInline

                    if (elementParentLocal.isNullSafeDereference)
                        return@simpleInline

                    val elementParentArrow = PhpPsiUtil.getNextSiblingIgnoreWhitespace(elementParentLocal.classReference, true) ?: return@simpleInline

                    if (!PhpPsiUtil.isOfType(elementParentArrow, PhpTokenTypes.ARROW))
                        return@simpleInline

                    elementParentArrow.insertBefore(FactoryService.createTernary(element.project))
                }
            )
        }
    }

    override fun getMinimumSupportedLanguageLevel(): PhpLanguageLevel =
        PhpLanguageLevel.PHP800
}
