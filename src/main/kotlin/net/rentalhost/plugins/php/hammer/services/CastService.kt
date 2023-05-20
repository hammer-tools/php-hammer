package net.rentalhost.plugins.php.hammer.services

object CastService {
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
}
