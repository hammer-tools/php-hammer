package net.rentalhost.plugins.services

object StringService {
    fun addQuotes(string: String, isComplex: Boolean, addSlashes: Boolean = true): String =
        if (isComplex) {
            if (addSlashes) "\"${string.replace("\\", "\\\\")}\""
            else "\"${string}\""
        }
        else "'${string}'"
}
