package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.google.common.collect.Iterables
import com.intellij.codeInspection.ProblemsHolder
import com.jetbrains.php.config.PhpLanguageLevel
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.PhpTypeDeclaration
import com.jetbrains.php.lang.psi.resolve.types.PhpType
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService
import net.rentalhost.plugins.php.hammer.services.TypeService

class NullableTypeRightmostInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        override fun visitPhpTypeDeclaration(element: PhpTypeDeclaration) {
            val elementTypeText = element.text

            if (elementTypeText.startsWith("?"))
                return

            val elementTypes = TypeService.splitTypes(elementTypeText)
                .map { s: String? -> if (s == "null") PhpType._NULL else s }
                .toList()

            if (!elementTypes.contains(PhpType._NULL) ||
                Iterables.getLast(elementTypes) == PhpType._NULL)
                return

            val elementTypeReplacementSuggestion = TypeService.joinTypes(TypeService.exceptNull(elementTypeText)) + "|null"

            ProblemsHolderService.instance.registerProblem(
                problemsHolder,
                element,
                "nullable type must be on rightmost side (\"$elementTypeReplacementSuggestion\")",
                QuickFixService.instance.simpleTypeReplace(
                    "Move \"null\" type to rightmost side",
                    elementTypeReplacementSuggestion
                )
            )
        }
    }

    override fun getMinimumSupportedLanguageLevel(): PhpLanguageLevel = PhpLanguageLevel.PHP800
}
