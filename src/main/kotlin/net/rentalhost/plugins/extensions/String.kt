package net.rentalhost.plugins.extensions

private val simplifyWhitespaceRegex: Regex = Regex("\\s{2,}")

fun String.simplifyWhitespace(): String =
    trim().replace(simplifyWhitespaceRegex, " ")

private val dropWhitespaceRegex: Regex = Regex("\\s+")

fun String.dropWhitespace(): String =
    replace(dropWhitespaceRegex, "")
