package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.google.common.collect.Iterables
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.jetbrains.php.config.PhpLanguageLevel
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.impl.PhpTypeDeclarationImpl
import com.jetbrains.php.lang.psi.resolve.types.PhpType
import net.rentalhost.plugins.services.LocalQuickFixService.SimpleTypeReplaceQuickFix
import net.rentalhost.plugins.services.ProblemsHolderService
import net.rentalhost.plugins.services.TypeService
import java.util.stream.Collectors

class NullableTypeRightmostInspection : PhpInspection() {
    override fun buildVisitor(
        problemsHolder: ProblemsHolder,
        isOnTheFly: Boolean
    ): PsiElementVisitor {
        return object : PsiElementVisitor() {
            override fun visitElement(element: PsiElement) {
                if (element is PhpTypeDeclarationImpl) {
                    val elementTypeText = element.getText()

                    if (elementTypeText.startsWith("?"))
                        return

                    val elementTypes = TypeService.splitTypes(elementTypeText)
                        .map { s: String? -> if (s == "null") PhpType._NULL else s }
                        .collect(Collectors.toList())

                    if (elementTypes.contains(PhpType._NULL) &&
                        Iterables.getLast(elementTypes) != PhpType._NULL) {
                        val elementTypeReplacementSuggestion = TypeService.joinTypesStream(TypeService.exceptNull(elementTypeText)) + "|null"

                        ProblemsHolderService.registerProblem(
                            problemsHolder,
                            element,
                            "Nullable type must be on rightmost side (\"$elementTypeReplacementSuggestion\").",
                            SimpleTypeReplaceQuickFix(elementTypeReplacementSuggestion, "Move \"null\" type to rightmost side")
                        )
                    }
                }
            }
        }
    }

    override fun getMinimumSupportedLanguageLevel(): PhpLanguageLevel {
        return PhpLanguageLevel.PHP800
    }
}
