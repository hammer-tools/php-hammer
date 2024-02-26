package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.refactoring.suggested.createSmartPointer
import com.intellij.util.xmlb.annotations.OptionTag
import com.jetbrains.php.config.PhpLanguageLevel
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.Parameter
import com.jetbrains.php.lang.psi.elements.impl.ParameterImpl
import com.jetbrains.php.lang.psi.elements.impl.PhpTypeDeclarationImpl
import com.jetbrains.php.lang.psi.resolve.types.PhpType
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.extensions.psi.withNull
import net.rentalhost.plugins.php.hammer.inspections.enums.OptionNullableTypeFormat
import net.rentalhost.plugins.php.hammer.services.*
import javax.swing.JComponent

class ParameterImplicitlyNullableInspection : PhpInspection() {
    @OptionTag
    var nullableTypeFormat: OptionNullableTypeFormat = OptionNullableTypeFormat.LONG

    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object : PhpElementVisitor() {
        override fun visitPhpParameter(element: Parameter) {
            if (element !is ParameterImpl ||
                !element.defaultValueType.isNullable
            )
                return

            val declaredType = element.declaredType

            if (declaredType.isEmpty ||
                declaredType.isNullable ||
                declaredType.types.contains(PhpType._MIXED)
            )
                return

            val localNullableTypeFormat =
                if (LanguageService.atLeast(problemsHolder.project, PhpLanguageLevel.PHP800)) nullableTypeFormat
                else OptionNullableTypeFormat.SHORT

            if (localNullableTypeFormat == OptionNullableTypeFormat.SHORT) {
                if (declaredType.hasIntersectionType()) return
                if (declaredType.types.size >= 2) return
            }

            val elementPointer = element.createSmartPointer()

            ProblemsHolderService.instance.registerProblem(
                problemsHolder,
                element,
                "parameter type is implicitly null",
                QuickFixService.instance.simpleInline("Add explicit \"null\" type") {
                    val elementLocal = elementPointer.element ?: return@simpleInline

                    if (elementLocal.typeDeclaration != null) {
                        with(elementLocal.typeDeclaration ?: return@simpleInline) {
                            replace(withNull(localNullableTypeFormat))
                        }
                    } else {
                        val parameterComplex = FactoryService.createComplexParameter(problemsHolder.project, elementLocal.text)

                        with(parameterComplex.typeDeclaration as PhpTypeDeclarationImpl) {
                            replace(withNull(nullableTypeFormat))
                        }

                        elementLocal.replace(
                            FactoryService.createComplexParameterDoctypeCompatible(
                                problemsHolder.project,
                                parameterComplex.text
                            )
                        )
                    }
                }
            )
        }
    }

    override fun getMinimumSupportedLanguageLevel(): PhpLanguageLevel = PhpLanguageLevel.PHP710

    override fun createOptionsPanel(): JComponent {
        return OptionsPanelService.create { component: OptionsPanelService ->
            component.delegateRadioCreation { radioComponent: OptionsPanelService.RadioComponent ->
                radioComponent.addOption(
                    "Prefer short format", nullableTypeFormat === OptionNullableTypeFormat.SHORT,
                    "Your nullable types will look like: <code>?int</code>"
                ) { nullableTypeFormat = OptionNullableTypeFormat.SHORT }

                radioComponent.addOption(
                    "Prefer long format (PHP 8.0+)", nullableTypeFormat === OptionNullableTypeFormat.LONG,
                    "Your nullable types will look like: <code>int|null</code>.<br/>" +
                            "Will default to the short format for projects with a PHP version lower than 8.0 automatically."
                ) { nullableTypeFormat = OptionNullableTypeFormat.LONG }
            }
        }
    }
}
