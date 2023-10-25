package net.rentalhost.plugins.php.hammer.inspections.codeSmell

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.*
import com.jetbrains.php.lang.psi.elements.impl.FieldImpl
import com.jetbrains.php.lang.psi.resolve.types.PhpType
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.php.hammer.extensions.psi.isName
import net.rentalhost.plugins.php.hammer.services.ClassService
import net.rentalhost.plugins.php.hammer.services.ProblemsHolderService

class UselessComparisonInspection : PhpInspection() {
  object Util {
    val typesSchemas = listOf(
      TypeSchema(
        listOf("\\is_int", "\\is_integer", "\\is_long"),
        "integer",
        listOf(PhpType._INT, PhpType._INTEGER)
      ),

      TypeSchema(
        listOf("\\is_float", "\\is_double"),
        "float",
        listOf(PhpType._FLOAT, PhpType._DOUBLE)
      ),

      TypeSchema(
        listOf("\\is_string"),
        "string",
        listOf(PhpType._STRING)
      ),

      TypeSchema(
        listOf("\\is_bool", "\\is_boolean"),
        "boolean",
        listOf(PhpType._BOOL, PhpType._BOOLEAN, PhpType._TRUE, PhpType._FALSE)
      ),

      TypeSchema(
        listOf("\\is_null"),
        "null",
        listOf(PhpType._NULL)
      ),

      TypeSchema(
        listOf("\\is_object"),
        "object",
        listOf(PhpType._OBJECT)
      ),

      TypeSchema(
        listOf("\\is_scalar"),
        "scalar",
        listOf(
          PhpType._STRING,
          PhpType._INT, PhpType._INTEGER,
          PhpType._BOOL, PhpType._BOOLEAN, PhpType._TRUE, PhpType._FALSE,
          PhpType._FLOAT, PhpType._DOUBLE
        )
      ),
    )

    data class TypeSchema(
      val functions: List<String>,
      val referenceType: String,
      val allowedTypes: List<String>,
    )

    data class ParameterType(
      val type: String,
      val phpClass: PhpClass? = null
    )

    fun resolveParameterType(parameter: PsiElement?): List<ParameterType>? {
      if (parameter !is PhpTypedElement)
        return null

      val parameterGlobalTypes = parameter.globalType

      if (parameterGlobalTypes.isAmbiguous)
        return null

      if (parameter is MemberReference) {
        val memberReference = parameter.resolve() as? FieldImpl ?: return null

        if (memberReference.typeDeclaration == null)
          return null

        if (!memberReference.hasDefaultValue() && !parameterGlobalTypes.isNullable)
          return null
      }

      val parameterTypes = parameterGlobalTypes.types

      if (parameterTypes.isEmpty())
        return null

      return parameterTypes.map {
        when {
          PhpType.isArray(PhpType.createParametrized(it)) -> ParameterType(PhpType._ARRAY)
          ClassService.findFQN(it, parameter.project) != null -> ParameterType(PhpType._OBJECT)
          else -> ParameterType(it)
        }
      }
    }
  }

  override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object : PhpElementVisitor() {
    override fun visitPhpFunctionCall(functionReference: FunctionReference) {
      val typeSchema = Util.typesSchemas.find { it -> it.functions.any { functionReference.isName(it) } } ?: return

      val parameter = functionReference.getParameter(0) as? PhpTypedElement ?: return
      val parameterTypes = Util.resolveParameterType(parameter) ?: return

      val parameterName =
        when (parameter) {
          is FunctionReference -> "${parameter.name}()"
          is ConstantReference, is MemberReference -> "argument"
          is PhpNamedElement -> parameter.nameNode?.text ?: return
          else -> parameter.name ?: "argument"
        }

      val reason = when {
        // Eg. int $x, $x is always int. Unique type: we could determine precisely incorrect usage of is_*().
        parameterTypes.size == 1 -> when {
          typeSchema.allowedTypes.contains(parameterTypes.first().type) -> "always"
          else -> "never"
        }

        // Eg. int|null $x. Multiple types: we could determine only `never` cases, like `is_string()`,
        // but we should skip `is_int()` because it depends of context.
        else -> when {
          typeSchema.allowedTypes.any { schemaType -> parameterTypes.any { it.type == schemaType } } -> return
          else -> "never"
        }
      }

      ProblemsHolderService.instance.registerProblem(
        problemsHolder,
        functionReference,
        "useless ${functionReference.name}(): $parameterName is $reason ${typeSchema.referenceType}"
      )
    }
  }
}
