package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInsight.intention.FileModifier
import com.intellij.codeInsight.intention.FileModifier.SafeFieldForPreview
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.codeInspection.options.OptCheckbox
import com.intellij.codeInspection.options.OptPane
import com.intellij.codeInspection.options.PlainMessage
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.text.HtmlChunk
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.SmartPointerManager
import com.intellij.psi.SmartPsiElementPointer
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.refactoring.suggested.createSmartPointer
import com.intellij.util.xmlb.annotations.OptionTag
import com.jetbrains.php.config.PhpLanguageFeature
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.ParameterList
import com.jetbrains.php.lang.psi.elements.impl.FunctionImpl
import com.jetbrains.php.lang.psi.elements.impl.MethodImpl
import com.jetbrains.php.lang.psi.elements.impl.ParameterImpl
import com.jetbrains.php.lang.psi.elements.impl.PhpPromotedFieldParameterImpl
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.extensions.psi.*
import net.rentalhost.plugins.php.hammer.services.FactoryService
import net.rentalhost.plugins.php.hammer.services.LanguageService
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.php.hammer.services.QuickFixService

class ParameterDefaultsNullInspection : PhpInspection() {
  @OptionTag
  var includeAbstractMethods: Boolean = false

  @OptionTag
  var includeOverriddenMethods: Boolean = false

  @OptionTag
  var includeNullableParameters: Boolean = false

  @OptionTag
  var includeParametersWithReference: Boolean = false

  @OptionTag
  var includeBooleans: Boolean = true

  @OptionTag
  var includeLatestParameter: Boolean = false

  override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object : PhpElementVisitor() {
    override fun visitPhpParameterList(element: ParameterList) {
      val context = element.context

      if (context !is FunctionImpl)
        return

      if (context is MethodImpl) {
        if (!context.isDefinedByOwnClass())
          return

        if (!includeOverriddenMethods && context.isMemberOverrided())
          return
      }

      val isAbstractMethod = context.isAbstractMethod()

      if (isAbstractMethod && !includeAbstractMethods)
        return

      val parameterLast = element.parameters.lastOrNull()

      for (parameter in element.parameters) {
        if (!includeLatestParameter && parameter === parameterLast) {
          continue
        }

        if (parameter is ParameterImpl) {
          if (parameter is PhpPromotedFieldParameterImpl && parameter.isReadonly)
            continue

          if (parameter.defaultValue != null) {
            if (!includeParametersWithReference &&
              parameter.isPassByRef)
              return

            val defaultValue = parameter.defaultValueType

            if (defaultValue.toString() == "null")
              continue

            if (!includeBooleans &&
              parameter.declaredType.toString() == "bool") {
              continue
            }

            if (!includeNullableParameters) {
              if (parameter.declaredType.toString() == "")
                continue

              if (parameter.typeDeclaration?.isNullableEx() == true)
                continue
            }

            ProblemsHolderService.instance.registerProblem(
              problemsHolder,
              parameter,
              "default value of the parameter must be \"null\"",
              run {
                if (isAbstractMethod ||
                  parameter.isPassByRef)
                  return@run null

                ReplaceWithNullQuickFix(
                  SmartPointerManager.createPointer(context),
                  SmartPointerManager.createPointer(parameter)
                )
              }
            )
          }
        }
      }
    }
  }

  override fun getOptionsPane(): OptPane {
    return OptPane.pane(
      OptCheckbox(
        "includeAbstractMethods",
        PlainMessage("Include abstract methods"),
        emptyList(),
        HtmlChunk.raw(
          "This option allows inspecting abstract methods, however, " +
            "a quick-fix will not be available and any action to correct this inspection will have to be done manually."
        )
      ),

      OptCheckbox(
        "includeOverriddenMethods",
        PlainMessage("Include methods that are overridden"),
        emptyList(),
        HtmlChunk.raw(
          "This option allows inspecting methods that have been overridden by other methods of child classes. " +
            "Although a quick-fix is available, refactoring may be required."
        )
      ),

      OptCheckbox(
        "includeNullableParameters",
        PlainMessage("Include nullable parameters"),
        emptyList(),
        HtmlChunk.raw(
          "This option allows inspecting nullable parameters, which implicitly include untyped parameters. " +
            "Although a quick-fix is available, it can affect the behavior of code."
        )
      ),

      OptCheckbox(
        "includeParametersWithReference",
        PlainMessage("Include parameters with reference"),
        emptyList(),
        HtmlChunk.raw(
          "This option allows you to inspect parameters that are passed by reference."
        )
      ),

      OptCheckbox(
        "includeBooleans",
        PlainMessage("Include booleans"),
        emptyList(),
        HtmlChunk.raw(
          "This option will include booleans in the analysis."
        )
      ),

      OptCheckbox(
        "includeLatestParameter",
        PlainMessage("Include last parameter"),
        emptyList(),
        HtmlChunk.raw(
          "This option will include the latest parameter in the analysis. This will not be necessary most of the time."
        )
      )
    )
  }

  class ReplaceWithNullQuickFix(
    @SafeFieldForPreview private var function: SmartPsiElementPointer<FunctionImpl>,
    @SafeFieldForPreview private var parameter: SmartPsiElementPointer<ParameterImpl>
  ) : QuickFixService.SimpleQuickFix("Smart replace with \"null\"") {
    override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
      val parameterDefaultValue = replaceDefaultValueWithNull(project)

      enforcesNullableType(project)

      createAssignment(project, parameterDefaultValue)
    }

    override fun getFileModifierForPreview(target: PsiFile): FileModifier {
      return ReplaceWithNullQuickFix(
        PsiTreeUtil.findSameElementInCopy(function.element, target).createSmartPointer(),
        PsiTreeUtil.findSameElementInCopy(parameter.element, target).createSmartPointer()
      )
    }

    private fun replaceDefaultValueWithNull(project: Project): PsiElement {
      val parameterDefaultValue = parameter.element!!.defaultValue!!

      parameterDefaultValue.replace(FactoryService.createConstantReference(project, "null"))

      return parameterDefaultValue
    }

    private fun enforcesNullableType(project: Project) {
      val parameterTypeDeclaration = (parameter.element ?: return).typeDeclaration ?: return

      if (parameterTypeDeclaration.isNullableEx())
        return

      parameterTypeDeclaration.replaceWith(project, parameterTypeDeclaration.text.substringAfter("?") + "|null")
    }

    private fun createAssignment(project: Project, parameterDefaultValue: PsiElement) {
      val parameterName = parameter.element?.name ?: return
      val parameterAssignment =
        if (parameter.element is PhpPromotedFieldParameterImpl) "this->$parameterName"
        else parameterName

      val variableAssignment = FactoryService.createAssignmentStatement(project, with(parameterAssignment) {
        when {
          LanguageService.hasFeature(project, PhpLanguageFeature.COALESCE_ASSIGN) -> "\$$this ??= ${parameterDefaultValue.text};"
          LanguageService.hasFeature(project, PhpLanguageFeature.COALESCE_OPERATOR) -> "\$$this = \$$parameterName ?? ${parameterDefaultValue.text};"
          else -> "\$$this = \$$parameterName === null ? ${parameterDefaultValue.text} : \$$parameterName;"
        }
      })

      with((function.element ?: return).functionBody()) {
        this!!.firstPsiChild.insertBeforeElse(variableAssignment, lazy {
          { replace(FactoryService.createFunctionBody(project, variableAssignment.text)) }
        })
      }
    }
  }
}
