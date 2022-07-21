package net.rentalhost.plugins.php.hammer.inspections.codeError

import com.intellij.codeInspection.ProblemsHolder
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.*
import com.jetbrains.php.lang.psi.resolve.types.PhpType
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.extensions.psi.getTypes
import net.rentalhost.plugins.services.ProblemsHolderService
import net.rentalhost.plugins.services.TypeService

class NativeMemberUsageInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        private fun visit(element: MemberReference) {
            val elementBase = element.firstPsiChild

            if (elementBase !is Variable)
                return

            val elementTypes = elementBase.getTypes().filter { it != PhpType._NULL }
            val elementTypesNatives = elementTypes.filter { TypeService.nativeTypes.contains(it) }

            if (elementTypesNatives.isEmpty())
                return

            if (elementTypes.size > elementTypesNatives.size)
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
}
