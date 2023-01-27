package net.rentalhost.plugins.php.hammer.inspections.codeWarning

import com.intellij.codeInspection.ProblemsHolder
import com.jetbrains.php.codeInsight.controlFlow.instructions.impl.PhpCallInstructionImpl
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.Method
import com.jetbrains.php.lang.psi.elements.PhpModifier
import com.jetbrains.php.lang.psi.elements.PhpReturn
import com.jetbrains.php.lang.psi.elements.impl.MethodImpl
import com.jetbrains.php.lang.psi.elements.impl.MethodReferenceImpl
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.hammer.extensions.psi.functionBody
import net.rentalhost.plugins.hammer.extensions.psi.getMemberOverridden
import net.rentalhost.plugins.hammer.extensions.psi.getSingleStatement
import net.rentalhost.plugins.hammer.extensions.psi.isAbstractMethod
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService

class MissingParentCallInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        override fun visitPhpMethod(method: Method) {
            if (method.isAbstract) return
            if ((method.containingClass ?: return).isTrait) return

            val methodIdentifier = method.nameIdentifier ?: return

            val baseClass = method.getMemberOverridden() ?: return
            val baseMethod = baseClass.findMethodByName(method.name) as? MethodImpl ?: return

            if (baseMethod.isAbstractMethod()) return
            if ((baseMethod.functionBody() ?: return).getSingleStatement() is PhpReturn) return
            if (baseMethod.scope.controlFlow.instructions.size == 2) return

            val methodName = method.name.lowercase()
            val methodCall = method.scope.controlFlow.instructions.find {
                it is PhpCallInstructionImpl &&
                with(it.functionReference as? MethodReferenceImpl ?: return@find false) {
                    referenceType == PhpModifier.State.PARENT &&
                    (name ?: return@find false).lowercase() == methodName
                }
            }

            if (methodCall != null) return

            ProblemsHolderService.instance.registerProblem(
                problemsHolder,
                methodIdentifier,
                "missing parent::${method.name}() call"
            )
        }
    }
}
