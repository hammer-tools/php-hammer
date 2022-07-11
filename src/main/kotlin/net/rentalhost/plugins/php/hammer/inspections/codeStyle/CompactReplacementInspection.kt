package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.impl.ArrayCreationExpressionImpl
import com.jetbrains.php.lang.psi.elements.impl.ArrayHashElementImpl
import com.jetbrains.php.lang.psi.elements.impl.StringLiteralExpressionImpl
import com.jetbrains.php.lang.psi.elements.impl.VariableImpl
import net.rentalhost.plugins.extensions.psi.unpackValues
import net.rentalhost.plugins.services.ProblemsHolderService
import net.rentalhost.plugins.services.TypeService

class CompactReplacementInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object: PsiElementVisitor() {
        override fun visitElement(element: PsiElement) {
            if (element is ArrayCreationExpressionImpl) {
                val arrayElements = element.unpackValues()

                if (arrayElements.isEmpty())
                    return

                for (arrayElement in arrayElements) {
                    if (arrayElement is ArrayHashElementImpl) {
                        val arrayElementKey = arrayElement.key as? StringLiteralExpressionImpl ?: return
                        val arrayElementValue = arrayElement.value as? VariableImpl ?: return

                        if (arrayElementKey.contents != arrayElementValue.name)
                            return
                    }
                    else if (TypeService.isVariadic(arrayElement)) {
                        return
                    }
                }

                ProblemsHolderService.registerProblem(
                    problemsHolder,
                    element,
                    "Array can be replaced by compact()."
                )
            }
        }
    }
}
