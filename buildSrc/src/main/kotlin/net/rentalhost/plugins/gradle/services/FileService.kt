package net.rentalhost.plugins.gradle.services

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path

object FileService {
  fun read(resourcePath: String): String =
    Files.readString(Path.of(resourcePath), StandardCharsets.UTF_8)
}
