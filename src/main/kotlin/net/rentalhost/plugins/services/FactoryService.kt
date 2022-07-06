package net.rentalhost.plugins.services

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiRecursiveElementVisitor
import com.jetbrains.php.lang.PhpFileType
import com.jetbrains.php.lang.lexer.PhpTokenTypes
import com.jetbrains.php.lang.psi.PhpPsiElementFactory
import com.jetbrains.php.lang.psi.elements.*
import com.jetbrains.php.lang.psi.elements.impl.AssignmentExpressionImpl
import com.jetbrains.php.lang.psi.elements.impl.GroupStatementImpl
import com.jetbrains.php.lang.psi.elements.impl.StatementImpl

object FactoryService {
    private fun <T: PhpPsiElement?> createPhpPsiFromText(
        project: Project,
        classTrail: MutableList<Class<out T>>,
        text: String
    ): T? {
        val classCast = classTrail.last()
        var elementExtracted: T? = null

        PsiFileFactory.getInstance(project).createFileFromText(
            "DUMMY__.${PhpFileType.INSTANCE.defaultExtension}", PhpFileType.INSTANCE,
            "<?php\n$text", System.currentTimeMillis(), false
        ).accept(object: PsiRecursiveElementVisitor() {
            override fun visitElement(element: PsiElement) {
                if (elementExtracted != null)
                    return

                if (classTrail.first().isInstance(element)) {
                    classTrail.removeFirst()

                    if (classTrail.isEmpty()) {
                        elementExtracted = classCast.cast(element)

                        return
                    }
                }

                element.acceptChildren(this)
            }
        })

        return elementExtracted
    }

    fun createColon(project: Project): PsiElement =
        PhpPsiElementFactory.createColon(project)

    fun createSemicolon(project: Project): PsiElement =
        PhpPsiElementFactory.createFromText(project, PhpTokenTypes.opSEMICOLON, ";")

    fun createReturnType(project: Project, returnType: String): PhpReturnType =
        PhpPsiElementFactory.createReturnType(project, returnType)

    fun createParameterType(project: Project, parameterType: String): PhpParameterType =
        PhpPsiElementFactory.createParameterType(project, parameterType)

    fun createComplexParameter(project: Project, parameterText: String): Parameter =
        PhpPsiElementFactory.createComplexParameter(project, parameterText)

    fun createComplexParameterDoctypeCompatible(project: Project, parameterText: String): Parameter =
        PhpPsiElementFactory.createPhpPsiFromText(project, Parameter::class.java, "/** @method a($parameterText) */")

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

    fun createArrayValue(project: Project, value: String): PhpPsiElement? =
        createPhpPsiFromText(project, mutableListOf(ArrayCreationExpression::class.java, PhpPsiElement::class.java), "[$value]")
}
