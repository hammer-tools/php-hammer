package net.rentalhost.plugins.services

import com.intellij.openapi.project.Project
import com.jetbrains.php.lang.psi.PhpPsiElementFactory
import com.jetbrains.php.lang.psi.elements.PhpFieldType
import com.jetbrains.php.lang.psi.elements.PhpParameterType
import com.jetbrains.php.lang.psi.elements.PhpReturnType

object FactoryService {
    fun createReturnType(project: Project, returnType: String): PhpReturnType =
        PhpPsiElementFactory.createReturnType(project, returnType)

    fun createParameterType(project: Project, parameterType: String): PhpParameterType =
        PhpPsiElementFactory.createParameterType(project, parameterType)

    fun createFieldType(project: Project, fieldType: String): PhpFieldType =
        PhpPsiElementFactory.createPhpPsiFromText(project, PhpFieldType::class.java, "class A{public $fieldType \$a}")
}
