package net.rentalhost.plugins.php.hammer.inspections.codeSmell

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.SmartPointerManager
import com.intellij.psi.util.PsiTreeUtil
import com.jetbrains.php.codeInsight.PhpScopeHolder
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.MethodReference
import com.jetbrains.php.lang.psi.elements.impl.ClassReferenceImpl
import com.jetbrains.php.lang.psi.elements.impl.MethodImpl
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.extensions.psi.functionBody
import net.rentalhost.plugins.extensions.psi.getMemberOverridden
import net.rentalhost.plugins.extensions.psi.isStrictlyStatement
import net.rentalhost.plugins.extensions.psi.isStub
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.ProblemsHolderService

class SenselessParentCallEmptyInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        override fun visitPhpMethodReference(element: MethodReference) {
            if (!element.parent.isStrictlyStatement())
                return

            val elementBase = element.firstPsiChild as? ClassReferenceImpl ?: return

            if (elementBase.text.lowercase() != "parent")
                return

            val elementScope = PsiTreeUtil.getParentOfType(element, PhpScopeHolder::class.java) as? MethodImpl ?: return
            val elementName = (element.name ?: return).lowercase()

            if (elementName.startsWith("__") ||
                elementName != elementScope.name.lowercase())
                return

            val baseClass = elementScope.getMemberOverridden() ?: return
            val baseMethod = baseClass.findMethodByName(elementScope.name) as? MethodImpl ?: return

            if (baseMethod.isStub())
                return

            val baseMethodChildren = (baseMethod.functionBody() ?: return)
                .text.substringAfter("{").substringBeforeLast("}").trim()

            if (baseMethodChildren != "")
                return

            ProblemsHolderService.registerProblem(
                problemsHolder,
                element,
                "senseless call to empty parent::${element.name}()",
                LocalQuickFixService.SimpleDeleteQuickFix(
                    "Delete call to parent::${element.name}()",
                    SmartPointerManager.createPointer(element.parent)
                )
            )
        }
    }
}
