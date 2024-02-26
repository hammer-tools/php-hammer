package net.rentalhost.plugins.php.hammer.extensions.psi

import com.intellij.openapi.project.Project
import com.jetbrains.php.lang.psi.elements.PhpTypeDeclaration
import com.jetbrains.php.lang.psi.elements.impl.PhpFieldTypeImpl
import com.jetbrains.php.lang.psi.elements.impl.PhpParameterTypeImpl
import com.jetbrains.php.lang.psi.elements.impl.PhpReturnTypeImpl
import net.rentalhost.plugins.php.hammer.inspections.enums.OptionNullableTypeFormat
import net.rentalhost.plugins.php.hammer.services.FactoryService
import net.rentalhost.plugins.php.hammer.services.TypeService

fun PhpTypeDeclaration.replaceWith(project: Project, typeReplacement: String) {
    this.replace(
        when (this) {
            is PhpReturnTypeImpl -> FactoryService.createReturnType(project, typeReplacement)
            is PhpParameterTypeImpl -> FactoryService.createParameterType(project, typeReplacement)
            is PhpFieldTypeImpl -> FactoryService.createFieldType(project, typeReplacement)
            else -> return
        }
    )
}

fun PhpTypeDeclaration.withNull(format: OptionNullableTypeFormat): PhpTypeDeclaration {
    if (this.isIntersection && text?.contains("(") != true)
        return FactoryService.createParameterType(project, "($text)|null")

    if (format == OptionNullableTypeFormat.LONG)
        return FactoryService.createParameterType(project, "$text|null")

    return FactoryService.createParameterType(project, "?$text")
}

fun PhpTypeDeclaration.isNullableEx(): Boolean =
    TypeService.splitTypes(this.text).anyMatch { s: String? -> s == "null" }
