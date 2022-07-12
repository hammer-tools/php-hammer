package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.MultiassignmentExpression
import com.jetbrains.php.lang.psi.elements.impl.ArrayCreationExpressionImpl
import com.jetbrains.php.lang.psi.elements.impl.ArrayHashElementImpl
import com.jetbrains.php.lang.psi.elements.impl.StringLiteralExpressionImpl
import com.jetbrains.php.lang.psi.elements.impl.VariableImpl
import net.rentalhost.plugins.extensions.psi.isRef
import net.rentalhost.plugins.extensions.psi.unpackValues
import net.rentalhost.plugins.services.FactoryService
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.ProblemsHolderService

class CompactReplacementInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object: PsiElementVisitor() {
        override fun visitElement(element: PsiElement) {
            if (element is ArrayCreationExpressionImpl &&
                element.parent !is MultiassignmentExpression) {
                val arrayElements = element.unpackValues()

                if (arrayElements.isEmpty())
                    return

                val arrayVariables = mutableListOf<String>()

                for (arrayElement in arrayElements) {
                    if (arrayElement !is ArrayHashElementImpl)
                        return

                    val arrayElementValue = arrayElement.value as? VariableImpl ?: return

                    if (arrayElementValue.isRef())
                        return

                    val arrayElementKey = arrayElement.key as? StringLiteralExpressionImpl ?: return

                    if (arrayElementKey.contents != arrayElementValue.name)
                        return

                    arrayVariables.add("'${arrayElementValue.name}'")
                }

                ProblemsHolderService.registerProblem(
                    problemsHolder,
                    element,
                    "Array can be replaced by compact().",
                    LocalQuickFixService.SimpleReplaceQuickFix(
                        "Replace with compact()",
                        FactoryService.createFunctionCall(problemsHolder.project, "compact", arrayVariables)
                    )
                )
            }
        }
    }
}
