package net.rentalhost.plugins.extensions

private val simplifyWhitespaceRegex: Regex = Regex("\\s{2,}")

fun String.simplifyWhitespace(): String =
    trim().replace(simplifyWhitespaceRegex, " ")
