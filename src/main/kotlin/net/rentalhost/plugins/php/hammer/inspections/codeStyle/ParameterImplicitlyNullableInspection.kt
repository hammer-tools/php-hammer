package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.jetbrains.php.config.PhpLanguageLevel
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.Parameter
import com.jetbrains.php.lang.psi.elements.impl.ParameterImpl
import com.jetbrains.php.lang.psi.elements.impl.PhpTypeDeclarationImpl
import com.jetbrains.php.lang.psi.resolve.types.PhpType
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.services.FactoryService
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.ProblemsHolderService

class ParameterImplicitlyNullableInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        override fun visitPhpParameter(element: Parameter) {
            if (element !is ParameterImpl ||
                !element.defaultValueType.isNullable)
                return

            val declaredType = element.declaredType

            if (declaredType.isEmpty ||
                declaredType.isNullable ||
                declaredType.types.contains(PhpType._MIXED))
                return

            ProblemsHolderService.registerProblem(
                problemsHolder,
                element,
                "Parameter type is implicitly null.",
                LocalQuickFixService.SimpleInlineQuickFix("Add explicit \"null\" type") {
                    if (element.typeDeclaration != null) {
                        (element.typeDeclaration ?: return@SimpleInlineQuickFix)
                            .replace(FactoryService.createParameterType(problemsHolder.project, (element.typeDeclaration ?: return@SimpleInlineQuickFix).text + "|null"))
                    }
                    else {
                        val parameterComplex = FactoryService.createComplexParameter(problemsHolder.project, element.text)

                        with(parameterComplex.typeDeclaration as PhpTypeDeclarationImpl) {
                            replace(FactoryService.createParameterType(problemsHolder.project, "$text|null"))
                        }

                        element.replace(FactoryService.createComplexParameterDoctypeCompatible(problemsHolder.project, parameterComplex.text))
                    }
                }
            )
        }
    }

    override fun getMinimumSupportedLanguageLevel(): PhpLanguageLevel = PhpLanguageLevel.PHP800
}
