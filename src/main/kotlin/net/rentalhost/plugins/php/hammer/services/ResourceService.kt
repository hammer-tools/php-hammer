package net.rentalhost.plugins.php.hammer.services

import java.nio.charset.StandardCharsets

object ResourceService {
    fun read(resourcePath: String): String =
        String(javaClass.getResource(resourcePath)!!.content as ByteArray, StandardCharsets.UTF_8)
}
