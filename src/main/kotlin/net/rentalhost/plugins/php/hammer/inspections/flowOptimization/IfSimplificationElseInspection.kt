package net.rentalhost.plugins.php.hammer.inspections.flowOptimization

import com.intellij.codeInspection.ProblemsHolder
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.Else
import com.jetbrains.php.lang.psi.elements.GroupStatement
import com.jetbrains.php.lang.psi.elements.If
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.extensions.psi.replaceWithGroupStatement
import net.rentalhost.plugins.services.FormatterService
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.ProblemsHolderService

class IfSimplificationElseInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        override fun visitPhpElse(element: Else) {
            val elementParent = element.parent as? If ?: return
            val elementReference = elementParent.elseIfBranches.lastOrNull() ?: elementParent

            val elementNormalized = FormatterService.normalizeText(element.statement ?: return)
            val elementReferenceNormalized = FormatterService.normalizeText(elementReference.statement ?: return)

            if (elementNormalized != elementReferenceNormalized)
                return

            ProblemsHolderService.registerProblem(
                problemsHolder,
                elementReference.firstChild,
                "Useless conditional can be safely dropped.",
                LocalQuickFixService.SimpleInlineQuickFix("Drop conditional") {
                    if (elementReference is If) {
                        val elementReferenceStatement = elementReference.statement

                        if (elementReferenceStatement !is GroupStatement ||
                            elementReference.parent is Else) {
                            elementReference.replace(elementReferenceStatement ?: return@SimpleInlineQuickFix)

                            return@SimpleInlineQuickFix
                        }

                        elementReference.replaceWithGroupStatement(elementReferenceStatement)
                    }

                    elementReference.delete()
                }
            )
        }
    }
}
