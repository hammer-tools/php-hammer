package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.SmartPointerManager
import com.intellij.psi.SmartPsiElementPointer
import com.intellij.util.xmlb.annotations.OptionTag
import com.jetbrains.php.config.PhpLanguageFeature
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.ParameterList
import com.jetbrains.php.lang.psi.elements.impl.FunctionImpl
import com.jetbrains.php.lang.psi.elements.impl.MethodImpl
import com.jetbrains.php.lang.psi.elements.impl.ParameterImpl
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.extensions.psi.*
import net.rentalhost.plugins.services.FactoryService
import net.rentalhost.plugins.services.LanguageService
import net.rentalhost.plugins.services.OptionsPanelService
import net.rentalhost.plugins.services.ProblemsHolderService
import javax.swing.JComponent

class ParameterDefaultsNullInspection: PhpInspection() {
    @OptionTag
    var includeAbstractMethods: Boolean = false

    @OptionTag
    var includeOverriddenMethods: Boolean = false

    @OptionTag
    var includeNullableParameters: Boolean = false

    @OptionTag
    var includeParametersWithReference: Boolean = false

    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
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

            for (parameter in element.parameters) {
                if (parameter is ParameterImpl &&
                    parameter.defaultValue != null) {
                    if (!includeParametersWithReference &&
                        parameter.isPassByRef)
                        return

                    val defaultValue = parameter.defaultValueType

                    if (defaultValue.toString() == "null")
                        continue

                    if (!includeNullableParameters) {
                        if (parameter.declaredType.toString() == "")
                            continue

                        if (parameter.typeDeclaration?.isNullableEx() == true)
                            continue
                    }

                    ProblemsHolderService.registerProblem(
                        problemsHolder,
                        parameter,
                        "Default value of the parameter must be \"null\".",
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

    override fun createOptionsPanel(): JComponent {
        return OptionsPanelService.create { component: OptionsPanelService ->
            component.addCheckbox(
                "Include abstract methods", includeAbstractMethods,
                "This option allows inspecting abstract methods, however, a quick-fix will not be available and any action to correct this inspection will have to be done manually."
            ) { includeAbstractMethods = it }

            component.addCheckbox(
                "Include methods that are overridden", includeOverriddenMethods,
                "This option allows inspecting methods that have been overridden by other methods of child classes. Although a quick-fix is available, refactoring may be required."
            ) { includeOverriddenMethods = it }

            component.addCheckbox(
                "Include nullable parameters", includeNullableParameters,
                "This option allows inspecting nullable parameters, which implicitly include untyped parameters. Although a quick-fix is available, it can affect the behavior of code."
            ) { includeNullableParameters = it }

            component.addCheckbox(
                "Include parameters with reference", includeParametersWithReference,
                "This option allows you to inspect parameters that are passed by reference."
            ) { includeParametersWithReference = it }
        }
    }

    class ReplaceWithNullQuickFix(
        private val function: SmartPsiElementPointer<FunctionImpl>,
        private val parameter: SmartPsiElementPointer<ParameterImpl>
    ): LocalQuickFix {
        override fun getFamilyName(): String = "Smart replace with \"null\""

        override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
            val parameterDefaultValue = replaceDefaultValueWithNull(project)

            enforcesNullableType(project)

            createAssignment(project, parameterDefaultValue)
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
            val variableAssignment = FactoryService.createAssignmentStatement(project, with((parameter.element ?: return).name) {
                when {
                    LanguageService.hasFeature(project, PhpLanguageFeature.COALESCE_ASSIGN) -> "\$$this ??= ${parameterDefaultValue.text};"
                    LanguageService.hasFeature(project, PhpLanguageFeature.COALESCE_OPERATOR) -> "\$$this = \$$this ?? ${parameterDefaultValue.text};"
                    else -> "\$$this = \$$this === null ? ${parameterDefaultValue.text} : \$$this;"
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
