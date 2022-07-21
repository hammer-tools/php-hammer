package net.rentalhost.plugins.php.hammer.inspections.codeError

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.util.xmlb.annotations.OptionTag
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.*
import com.jetbrains.php.lang.psi.resolve.types.PhpType
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.extensions.psi.getTypes
import net.rentalhost.plugins.services.OptionsPanelService
import net.rentalhost.plugins.services.ProblemsHolderService
import net.rentalhost.plugins.services.TypeService
import javax.swing.JComponent

class NativeMemberUsageInspection: PhpInspection() {
    @OptionTag
    var strictChecking: Boolean = true

    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        private fun visit(element: MemberReference) {
            val elementBase = element.firstPsiChild

            if (elementBase !is Variable)
                return

            val elementTypes = elementBase.getTypes().filter { it != PhpType._NULL }
            val elementTypesNatives = elementTypes.filter { TypeService.nativeTypes.contains(it) }

            if (elementTypesNatives.isEmpty())
                return

            if (!strictChecking && elementTypes.size > elementTypesNatives.size)
                return

            ProblemsHolderService.registerProblem(
                problemsHolder,
                elementBase,
                "Native type must not be used as object."
            )
        }

        override fun visitPhpClassConstantReference(element: ClassConstantReference) = visit(element)
        override fun visitPhpFieldReference(element: FieldReference) = visit(element)
        override fun visitPhpMethodReference(element: MethodReference) = visit(element)
    }

    override fun createOptionsPanel(): JComponent {
        return OptionsPanelService.create { component: OptionsPanelService ->
            component.addCheckbox(
                "Strict check", strictChecking,
                "This option makes this checking strict, meaning that variables that mix native types with object types will also be inspected."
            ) { strictChecking = it }
        }
    }
}
