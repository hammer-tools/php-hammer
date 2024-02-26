package net.rentalhost.plugins.php.hammer.inspections.codeSmell

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.refactoring.suggested.createSmartPointer
import com.jetbrains.php.codeInsight.PhpScopeHolder
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.MethodReference
import com.jetbrains.php.lang.psi.elements.impl.ClassReferenceImpl
import com.jetbrains.php.lang.psi.elements.impl.MethodImpl
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.extensions.psi.getMemberOverridden
import net.rentalhost.plugins.php.hammer.extensions.psi.isStrictlyStatement
import net.rentalhost.plugins.php.hammer.extensions.psi.isStub
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService

class SenselessParentCallEmptyInspection : PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object : PhpElementVisitor() {
        override fun visitPhpMethodReference(element: MethodReference) {
            if (!element.parent.isStrictlyStatement())
                return

            val elementBase = element.firstPsiChild as? ClassReferenceImpl ?: return

            if (elementBase.text.lowercase() != "parent")
                return

            val elementScope = PsiTreeUtil.getParentOfType(element, PhpScopeHolder::class.java) as? MethodImpl ?: return
            val elementName = (element.name ?: return).lowercase()

            if (elementName.startsWith("__") || elementName != elementScope.name.lowercase())
                return

            val baseClass = elementScope.getMemberOverridden() ?: return
            val baseMethod = baseClass.findMethodByName(elementScope.name) as? MethodImpl ?: return

            if (baseMethod.isStub())
                return

            if (baseMethod.scope.controlFlow.instructions.size > 2)
                return

            ProblemsHolderService.instance.registerProblem(
                problemsHolder,
                element,
                "senseless call to empty parent::${element.name}()",
                QuickFixService.instance.simpleDelete(
                    "Delete call to parent::${element.name}()",
                    element.parent.createSmartPointer()
                )
            )
        }
    }
}
