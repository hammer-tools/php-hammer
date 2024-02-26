package net.rentalhost.plugins.php.hammer.services

import com.jetbrains.php.lang.psi.elements.StringLiteralExpression

object StringService {
    fun addQuotes(string: String, isComplex: Boolean, addSlashes: Boolean = true): String =
        if (isComplex) {
            if (addSlashes) "\"${string.replace("\\", "\\\\")}\""
            else "\"${string}\""
        } else "'${string}'"

    fun unescapeString(string: StringLiteralExpression): String = StringBuilder().also {
        string.createLiteralTextEscaper().decode(string.valueRange, it)
    }.toString()
}
