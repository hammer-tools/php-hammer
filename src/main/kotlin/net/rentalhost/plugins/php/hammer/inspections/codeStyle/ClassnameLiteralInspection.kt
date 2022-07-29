package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.jetbrains.php.lang.PhpLangUtil
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.BinaryExpression
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.services.ClassService
import net.rentalhost.plugins.services.LanguageService
import net.rentalhost.plugins.services.ProblemsHolderService
import net.rentalhost.plugins.services.TypeService

class ClassnameLiteralInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        override fun visitPhpStringLiteralExpression(string: StringLiteralExpression) {
            if (!string.contents.contains("\\"))
                return

            val stringParent = string.parent

            if (stringParent is BinaryExpression &&
                !TypeService.compareOperations.contains(stringParent.operationType))
                return

            val stringBuilder = StringBuilder()

            if (!string.createLiteralTextEscaper().decode(string.valueRange, stringBuilder))
                return

            val stringNormalized = stringBuilder.toString()

            if (!LanguageService.isNamespacedClassname(stringNormalized))
                return

            val classname = PhpLangUtil.toFQN(stringNormalized)

            ClassService.findFQN(classname, string.project) ?: return

            ProblemsHolderService.registerProblem(
                problemsHolder,
                string,
                "String can be replaced by ::class equivalent."
            )
        }
    }
}
