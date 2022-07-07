package net.rentalhost.plugins.gradle.services

private val uppercaseRegex = Regex("[A-Z]")

object StringService {
    fun dashCase(string: String): String =
        uppercaseRegex.replace(string) { "-" + it.value.toLowerCase() }.drop(1)
}
