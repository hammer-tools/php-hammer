package net.rentalhost.plugins.gradle.services

import java.io.InputStream
import java.nio.charset.StandardCharsets

object ResourceService {
    fun read(resourcePath: String): String =
        String((javaClass.getResource(resourcePath)!!.content as InputStream).readBytes(), StandardCharsets.UTF_8)
}
