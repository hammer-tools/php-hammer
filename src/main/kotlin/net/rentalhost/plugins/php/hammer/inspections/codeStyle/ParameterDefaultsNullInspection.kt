package net.rentalhost.plugins.php.hammer.inspections.codeStyle

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.SmartPointerManager
import com.intellij.psi.SmartPsiElementPointer
import com.jetbrains.php.config.PhpLanguageFeature
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.impl.FunctionImpl
import com.jetbrains.php.lang.psi.elements.impl.MethodImpl
import com.jetbrains.php.lang.psi.elements.impl.ParameterImpl
import com.jetbrains.php.lang.psi.elements.impl.ParameterListImpl
import net.rentalhost.plugins.extensions.psi.*
import net.rentalhost.plugins.services.FactoryService
import net.rentalhost.plugins.services.LanguageService
import net.rentalhost.plugins.services.OptionsPanelService
import net.rentalhost.plugins.services.ProblemsHolderService
import javax.swing.JComponent

class ParameterDefaultsNullInspection: PhpInspection() {
    public var includeAbstractMethods: Boolean = true

    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object: PsiElementVisitor() {
        override fun visitElement(element: PsiElement) {
            if (element is ParameterListImpl) {
                val context = element.context

                if (context !is FunctionImpl)
                    return

                if (context is MethodImpl && !context.isDefinedByOwnClass())
                    return

                for (parameter in element.parameters) {
                    if (parameter is ParameterImpl &&
                        parameter.defaultValue != null) {
                        val isAbstractMethod = context.isAbstractMethod()

                        if (isAbstractMethod && !includeAbstractMethods)
                            return

                        val defaultValue = parameter.defaultValueType

                        if (defaultValue.toString() != "null") {
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
        }
    }

    override fun createOptionsPanel(): JComponent {
        return OptionsPanelService.create { component: OptionsPanelService ->
            component.addCheckbox("Include abstract methods", includeAbstractMethods) { includeAbstractMethods = it }
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
            val parameterTypeDeclaration = parameter.element!!.typeDeclaration ?: return

            if (parameterTypeDeclaration.isNullableEx())
                return

            parameterTypeDeclaration.replaceWith(project, parameterTypeDeclaration.text + "|null")
        }

        private fun createAssignment(project: Project, parameterDefaultValue: PsiElement) {
            val variableAssignment = FactoryService.createAssignmentStatement(project, with(parameter.element!!.name) {
                when {
                    LanguageService.hasFeature(project, PhpLanguageFeature.COALESCE_ASSIGN) -> "\$$this ??= ${parameterDefaultValue.text};"
                    LanguageService.hasFeature(project, PhpLanguageFeature.COALESCE_OPERATOR) -> "\$$this = \$$this ?? ${parameterDefaultValue.text};"
                    else -> "\$$this = \$$this === null ? ${parameterDefaultValue.text} : \$$this;"
                }
            })

            with(function.element!!.functionBody()) {
                this!!.firstPsiChild.insertBeforeElse(variableAssignment, lazy {
                    { replace(FactoryService.createFunctionBody(project, variableAssignment.text)) }
                })
            }
        }
    }
}
