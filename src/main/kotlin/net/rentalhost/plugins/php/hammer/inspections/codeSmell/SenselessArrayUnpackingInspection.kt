package net.rentalhost.plugins.php.hammer.inspections.codeSmell

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.refactoring.suggested.createSmartPointer
import com.jetbrains.php.config.PhpLanguageLevel
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.ArrayCreationExpression
import com.jetbrains.php.lang.psi.elements.PhpTypedElement
import com.jetbrains.php.lang.psi.elements.impl.ArrayCreationExpressionImpl
import com.jetbrains.php.lang.psi.resolve.types.PhpType
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.hammer.extensions.psi.hasInterface
import net.rentalhost.plugins.hammer.extensions.psi.isVariadicPreceded
import net.rentalhost.plugins.hammer.services.ClassService
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService

class SenselessArrayUnpackingInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        override fun visitPhpArrayCreationExpression(expression: ArrayCreationExpression) {
            val expressionElements = (expression as ArrayCreationExpressionImpl).values().toList()

            if (expressionElements.size != 1)
                return

            val expressionElement = expressionElements.first()

            if (expressionElement !is PhpTypedElement ||
                expressionElement is ArrayCreationExpression ||
                !expressionElement.isVariadicPreceded())
                return

            val expressionType = expressionElement.globalType

            expressionType.types.forEach {
                if (PhpType.isArray(it) ||
                    PhpType.isPluralType(it))
                    return@forEach

                if (it.equals("\\Generator") || !it.startsWith("\\"))
                    return

                val expressionClass = ClassService.findFQN(it, expression.project) ?: return

                if (!expressionClass.hasInterface("\\Traversable"))
                    return
            }

            val expressionElementPointer = expressionElement.createSmartPointer()

            ProblemsHolderService.instance.registerProblem(
                problemsHolder,
                expression,
                "unnecessary array unpacking",
                QuickFixService.instance.simpleReplace("Replace with own variable", expressionElementPointer)
            )
        }
    }

    override fun getMinimumSupportedLanguageLevel(): PhpLanguageLevel = PhpLanguageLevel.PHP740
}
