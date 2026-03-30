package net.rentalhost.plugins.php.hammer.extensions.kotlin

fun String.capitalize(): String =
    replaceFirstChar { it.uppercase() }
