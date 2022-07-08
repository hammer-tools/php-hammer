package net.rentalhost.plugins.services

import com.intellij.util.containers.reverse
import com.jetbrains.php.lang.lexer.PhpTokenTypes

object CastService {
    @Suppress("SpellCheckingInspection")
    val castFunctions: Map<String, String> = mapOf(
        Pair("boolval", "bool"),
        Pair("intval", "int"),
        Pair("floatval", "float"),
        Pair("doubleval", "float"),
        Pair("strval", "string")
    )

    val castSetType: Map<String, String> = mapOf(
        Pair("bool", "bool"),
        Pair("boolean", "bool"),
        Pair("int", "int"),
        Pair("integer", "int"),
        Pair("float", "float"),
        Pair("double", "float"),
        Pair("string", "string"),
        Pair("array", "array"),
        Pair("object", "object"),
        Pair("null", "null"),
    )

    val castNormalizationGeneral = mapOf(
        Pair("double", "float"),
        Pair("real", "float"),
    )

    val castNormalizationShort = mapOf(
        Pair("integer", "int"),
        Pair("boolean", "bool"),
    )

    val castNormalizationLong = castNormalizationShort.reverse()

    val castNormalizationTypes = listOf(PhpTokenTypes.opINTEGER_CAST, PhpTokenTypes.opFLOAT_CAST, PhpTokenTypes.opBOOLEAN_CAST)
}
