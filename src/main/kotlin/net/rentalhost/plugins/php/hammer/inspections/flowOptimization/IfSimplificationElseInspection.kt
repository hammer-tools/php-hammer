package net.rentalhost.plugins.php.hammer.inspections.flowOptimization

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.refactoring.suggested.createSmartPointer
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.Else
import com.jetbrains.php.lang.psi.elements.GroupStatement
import com.jetbrains.php.lang.psi.elements.If
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.extensions.psi.replaceWithGroupStatement
import net.rentalhost.plugins.php.hammer.services.FormatterService
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService

class IfSimplificationElseInspection : PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object : PhpElementVisitor() {
        override fun visitPhpElse(element: Else) {
            val elementParent = element.parent as? If ?: return
            val elementReference = elementParent.elseIfBranches.lastOrNull() ?: elementParent

            val elementNormalized = FormatterService.normalize(element.statement ?: return)
            val elementReferenceNormalized = FormatterService.normalize(elementReference.statement ?: return)

            if (elementNormalized != elementReferenceNormalized)
                return

            val elementReferencePointer = elementReference.createSmartPointer()

            ProblemsHolderService.instance.registerProblem(
                problemsHolder,
                elementReference.firstChild,
                "useless conditional can be safely dropped",
                QuickFixService.instance.simpleInline("Drop conditional") {
                    val elementReferenceLocal = elementReferencePointer.element ?: return@simpleInline

                    if (elementReferenceLocal is If) {
                        val elementReferenceStatement = elementReferenceLocal.statement

                        if (elementReferenceStatement !is GroupStatement ||
                            elementReferenceLocal.parent is Else
                        ) {
                            elementReferenceLocal.replace(elementReferenceStatement ?: return@simpleInline)

                            return@simpleInline
                        }

                        elementReferenceLocal.replaceWithGroupStatement(elementReferenceStatement)
                    }

                    elementReferenceLocal.delete()
                }
            )
        }
    }
}
