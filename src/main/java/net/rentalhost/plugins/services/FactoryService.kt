package net.rentalhost.plugins.services

import com.intellij.openapi.project.Project
import com.jetbrains.php.lang.psi.PhpPsiElementFactory
import com.jetbrains.php.lang.psi.elements.*
import com.jetbrains.php.lang.psi.elements.impl.AssignmentExpressionImpl
import com.jetbrains.php.lang.psi.elements.impl.GroupStatementImpl
import com.jetbrains.php.lang.psi.elements.impl.StatementImpl

object FactoryService {
    fun createReturnType(project: Project, returnType: String): PhpReturnType =
        PhpPsiElementFactory.createReturnType(project, returnType)

    fun createParameterType(project: Project, parameterType: String): PhpParameterType =
        PhpPsiElementFactory.createParameterType(project, parameterType)

    fun createFieldType(project: Project, fieldType: String): PhpFieldType =
        PhpPsiElementFactory.createPhpPsiFromText(project, PhpFieldType::class.java, "class A{public $fieldType \$a}")

    fun createFunctionUse(project: Project, useVariables: String): PhpUseList =
        PhpPsiElementFactory.createPhpPsiFromText(project, PhpUseList::class.java, "function () use($useVariables) {}")

    fun createConstantReference(project: Project, constantName: String): ConstantReference =
        PhpPsiElementFactory.createConstantReference(project, constantName)

    fun createFunctionBody(project: Project, functionBody: String): GroupStatementImpl =
        PhpPsiElementFactory.createPhpPsiFromText(project, GroupStatementImpl::class.java, "function () {$functionBody}")

    fun createAssignmentStatement(project: Project, assignment: String): StatementImpl =
        PhpPsiElementFactory.createPhpPsiFromText(project, AssignmentExpressionImpl::class.java, assignment).parent as StatementImpl
}
