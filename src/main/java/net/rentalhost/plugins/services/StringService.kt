package net.rentalhost.plugins.services

object StringService {
    private val whitespaceRegex: Regex = Regex("\\s{2,}")

    fun simplifyWhitespace(text: String): String {
        return text.trim().replace(whitespaceRegex, " ")
    }
}
