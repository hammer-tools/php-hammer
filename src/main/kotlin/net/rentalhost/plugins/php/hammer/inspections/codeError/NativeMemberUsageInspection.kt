package net.rentalhost.plugins.php.hammer.inspections.codeError

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.codeInspection.options.OptCheckbox
import com.intellij.codeInspection.options.OptPane
import com.intellij.codeInspection.options.PlainMessage
import com.intellij.openapi.util.text.HtmlChunk
import com.intellij.util.xmlb.annotations.OptionTag
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.*
import com.jetbrains.php.lang.psi.resolve.types.PhpType
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.extensions.psi.getTypes
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.TypeService

class NativeMemberUsageInspection : PhpInspection() {
    @OptionTag
    var strictChecking: Boolean = true

    @OptionTag
    var includeStaticCall: Boolean = true

    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object : PhpElementVisitor() {
        private fun visit(element: MemberReference) {
            val elementBase = element.firstPsiChild

            if (elementBase !is Variable)
                return

            val elementTypes = elementBase.getTypes().filter { it != PhpType._NULL }

            if (elementTypes.contains("\\class-string"))
                return

            val elementTypesNatives = elementTypes.filter { TypeService.nativeTypes.contains(it) }

            if (elementTypesNatives.isEmpty())
                return

            if (!strictChecking && elementTypes.size > elementTypesNatives.size)
                return

            if (!includeStaticCall && element.isStatic && elementTypesNatives.all { it == PhpType._STRING }) {
                if (element is FieldReference ||
                    element is MethodReference
                )
                    return
            }

            ProblemsHolderService.instance.registerProblem(problemsHolder, elementBase, "native type must not be used as object")
        }

        override fun visitPhpClassConstantReference(element: ClassConstantReference) = visit(element)
        override fun visitPhpFieldReference(element: FieldReference) = visit(element)
        override fun visitPhpMethodReference(element: MethodReference) = visit(element)
    }

    override fun getOptionsPane(): OptPane {
        return OptPane.pane(
            OptCheckbox(
                "strictChecking",
                PlainMessage("Strict check"),
                emptyList(),
                HtmlChunk.raw(
                    "This option makes this checking strict, meaning that variables that mix native types with object types will also be inspected."
                )
            ),

            OptCheckbox(
                "includeStaticCall",
                PlainMessage("Include static call"),
                emptyList(),
                HtmlChunk.raw(
                    "This option includes calls to static methods or properties access on <code>string</code> type. " +
                            "This condition is sometimes valid when the string contains the name of a class. " +
                            "Try keeping this option enabled, and alternatively use a <code>@var</code> phpdoc " +
                            "declaring the type of class that must be present in the string."
                )
            )
        )
    }
}
