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
import net.rentalhost.plugins.php.hammer.inspections.enums.OptionNullableTypeFormat
import net.rentalhost.plugins.php.hammer.services.FactoryService
import net.rentalhost.plugins.php.hammer.services.OptionsPanelService
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService
import javax.swing.JComponent

class ParameterImplicitlyNullableInspection : PhpInspection() {
  @OptionTag
  var nullableTypeFormat: OptionNullableTypeFormat = OptionNullableTypeFormat.LONG

  override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object : PhpElementVisitor() {
    override fun visitPhpParameter(element: Parameter) {
      if (element !is ParameterImpl ||
        !element.defaultValueType.isNullable)
        return

      val declaredType = element.declaredType

      if (declaredType.isEmpty ||
        declaredType.isNullable ||
        declaredType.types.contains(PhpType._MIXED))
        return

      if (nullableTypeFormat == OptionNullableTypeFormat.SHORT &&
        declaredType.types.size >= 2)
        return

      val elementPointer = element.createSmartPointer()

      ProblemsHolderService.instance.registerProblem(
        problemsHolder,
        element,
        "parameter type is implicitly null",
        QuickFixService.instance.simpleInline("Add explicit \"null\" type") {
          val elementLocal = elementPointer.element ?: return@simpleInline

          if (elementLocal.typeDeclaration != null) {
            (elementLocal.typeDeclaration ?: return@simpleInline)
              .replace(
                FactoryService.createParameterType(
                  problemsHolder.project,
                  getParameterReplacement((elementLocal.typeDeclaration ?: return@simpleInline).text)
                )
              )
          }
          else {
            val parameterComplex = FactoryService.createComplexParameter(problemsHolder.project, elementLocal.text)

            with(parameterComplex.typeDeclaration as PhpTypeDeclarationImpl) {
              replace(
                FactoryService.createParameterType(
                  problemsHolder.project,
                  getParameterReplacement(text)
                )
              )
            }

            elementLocal.replace(FactoryService.createComplexParameterDoctypeCompatible(problemsHolder.project, parameterComplex.text))
          }
        }
      )
    }
  }

  private fun getParameterReplacement(text: String?): String {
    if (nullableTypeFormat == OptionNullableTypeFormat.LONG)
      return "$text|null"

    return "?$text"
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
          "Prefer long format", nullableTypeFormat === OptionNullableTypeFormat.LONG,
          "Your nullable types will look like: <code>int|null</code>"
        ) { nullableTypeFormat = OptionNullableTypeFormat.LONG }
      }
    }
  }
}
