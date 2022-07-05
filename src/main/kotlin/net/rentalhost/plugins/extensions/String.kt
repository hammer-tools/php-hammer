package net.rentalhost.plugins.extensions

private val whitespaceRegex: Regex = Regex("\\s{2,}")

fun String.simplifyWhitespace(): String =
    trim().replace(whitespaceRegex, " ")
