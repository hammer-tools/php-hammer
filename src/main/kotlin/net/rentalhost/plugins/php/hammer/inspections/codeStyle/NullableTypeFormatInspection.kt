package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.util.xmlb.annotations.OptionTag
import com.jetbrains.php.config.PhpLanguageLevel
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.PhpTypeDeclaration
import com.jetbrains.php.lang.psi.resolve.types.PhpType
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.inspections.enums.OptionNullableTypeFormat
import net.rentalhost.plugins.php.hammer.services.OptionsPanelService
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService
import net.rentalhost.plugins.php.hammer.services.TypeService
import javax.swing.JComponent

class NullableTypeFormatInspection : PhpInspection() {
  @OptionTag
  var nullableTypeFormat: OptionNullableTypeFormat = OptionNullableTypeFormat.LONG

  override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object : PhpElementVisitor() {
    override fun visitPhpTypeDeclaration(element: PhpTypeDeclaration) {
      val elementType = element.type

      if (elementType.hasIntersectionType()) return

      val elementTypes = elementType.types

      if (elementTypes.size != 2 ||
        !elementTypes.contains(PhpType._NULL))
        return

      val elementTypeText = element.text
      val elementTypeIsShort = elementTypeText.startsWith("?")
      var elementTypeReplacementSuggestion: String? = null

      if (elementTypeIsShort && nullableTypeFormat === OptionNullableTypeFormat.LONG) {
        elementTypeReplacementSuggestion = "${elementTypeText.substring(1)}|null"
      }
      else if (!elementTypeIsShort && nullableTypeFormat === OptionNullableTypeFormat.SHORT) {
        val elementTypeSingular = TypeService.exceptNull(elementTypeText).findFirst()

        if (elementTypeSingular.isPresent) {
          elementTypeReplacementSuggestion = "?${elementTypeSingular.get()}"
        }
      }

      if (elementTypeReplacementSuggestion == null)
        return

      ProblemsHolderService.instance.registerProblem(
        problemsHolder,
        element,
        "nullable type must be written as \"$elementTypeReplacementSuggestion\"",
        QuickFixService.instance.simpleTypeReplace(
          if (nullableTypeFormat === OptionNullableTypeFormat.LONG) "Replace with the long format"
          else "Replace with the short format",
          elementTypeReplacementSuggestion
        )
      )
    }
  }

  override fun getMinimumSupportedLanguageLevel(): PhpLanguageLevel = PhpLanguageLevel.PHP800

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
