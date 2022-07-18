package net.rentalhost.plugins.services

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiRecursiveElementVisitor
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.jetbrains.php.lang.PhpFileType
import com.jetbrains.php.lang.lexer.PhpTokenTypes
import com.jetbrains.php.lang.psi.PhpPsiElementFactory
import com.jetbrains.php.lang.psi.elements.*
import com.jetbrains.php.lang.psi.elements.impl.AssignmentExpressionImpl
import com.jetbrains.php.lang.psi.elements.impl.GroupStatementImpl
import com.jetbrains.php.lang.psi.elements.impl.StatementImpl
import com.jetbrains.php.lang.psi.elements.impl.UnaryExpressionImpl

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

    fun createStaticKeyword(project: Project): PsiElement =
        PhpPsiElementFactory.createFromText(project, PhpTokenTypes.kwSTATIC, "static")

    fun createExpression(project: Project, expression: String): PhpExpression =
        PhpPsiElementFactory.createPhpPsiFromText(project, PhpExpression::class.java, "$expression;")

    fun createReturnType(project: Project, returnType: String): PhpReturnType =
        PhpPsiElementFactory.createReturnType(project, returnType)

    fun createFunctionCall(project: Project, functionName: String, parameters: List<String>): PsiElement =
        PhpPsiElementFactory.createFromText(project, PsiElement::class.java, "$functionName(${parameters.joinToString(",")});", intArrayOf(0, 2, 0))

    fun createFunctionCall(project: Project, not: Boolean, functionName: String, parameters: List<String>): PsiElement =
        createFunctionCall(project, if (not) "!$functionName" else functionName, parameters)

    fun createFunctionCallable(project: Project, functionName: String): PhpCallableFunction =
        PhpPsiElementFactory.createPhpPsiFromText(project, PhpCallableFunction::class.java, "$functionName(...);")

    fun createParameterType(project: Project, parameterType: String): PhpParameterType =
        PhpPsiElementFactory.createParameterType(project, parameterType)

    fun createClassReference(project: Project, qualifiedName: String): ClassReference =
        PhpPsiElementFactory.createClassReference(project, qualifiedName)

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

    fun createStatement(project: Project, statement: String): Statement =
        PhpPsiElementFactory.createStatement(project, statement)

    fun createAssignmentStatement(project: Project, assignment: String): StatementImpl =
        PhpPsiElementFactory.createPhpPsiFromText(project, AssignmentExpressionImpl::class.java, assignment).parent as StatementImpl

    fun createArrayEmpty(project: Project): ArrayCreationExpression =
        PhpPsiElementFactory.createPhpPsiFromText(project, ArrayCreationExpression::class.java, "[];")

    fun createArrayValue(project: Project, value: String): PhpPsiElement? =
        createPhpPsiFromText(project, mutableListOf(ArrayCreationExpression::class.java, PhpPsiElement::class.java), "[$value]")

    fun createArrayKeyValue(project: Project, key: String, value: String): ArrayHashElement =
        PhpPsiElementFactory.createPhpPsiFromText(project, ArrayHashElement::class.java, "[$key=>$value]")

    fun createTypeCastExpression(project: Project, castType: String, expression: String): UnaryExpression =
        PhpPsiElementFactory.createPhpPsiFromText(project, UnaryExpression::class.java, "($castType) $expression")

    fun createTypeCast(project: Project, castType: String): LeafPsiElement =
        PhpPsiElementFactory.createPhpPsiFromText(project, UnaryExpression::class.java, "$castType\$x").firstChild as LeafPsiElement

    fun createTypeCastDouble(project: Project, castTypeFinal: String, castTypeInitial: String, value: String): UnaryExpression =
        PhpPsiElementFactory.createPhpPsiFromText(project, UnaryExpression::class.java, "($castTypeFinal)($castTypeInitial)$value")

    fun createCurlyVariable(project: Project, variableName: String): PsiElement =
        PhpPsiElementFactory.createPhpPsiFromText(project, Variable::class.java, "\"{\$$variableName}\";")

    fun createUnaryRightOperation(project: Project, element: String, operator: String): UnaryExpressionImpl =
        PhpPsiElementFactory.createPhpPsiFromText(project, UnaryExpressionImpl::class.java, "$element$operator;")

    fun createUnaryLeftOperation(project: Project, element: String, operator: String): UnaryExpressionImpl =
        PhpPsiElementFactory.createPhpPsiFromText(project, UnaryExpressionImpl::class.java, "$operator$element;")

    fun createBinaryExpression(project: Project, expression: String): BinaryExpression =
        PhpPsiElementFactory.createPhpPsiFromText(project, BinaryExpression::class.java, "$expression;")

    fun createComparisonExpression(project: Project, leftOperand: String, operator: String, rightOperand: String): BinaryExpression =
        createBinaryExpression(project, "$leftOperand$operator$rightOperand")

    fun createWhiteSpace(project: Project, whitespace: String = " "): PsiWhiteSpace =
        PsiFileFactory.getInstance(project).createFileFromText(
            "DUMMY__.${PhpFileType.INSTANCE.defaultExtension}", PhpFileType.INSTANCE,
            "<?php$whitespace", System.currentTimeMillis(), false
        ).children[1] as PsiWhiteSpace
}
