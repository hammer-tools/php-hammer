package net.rentalhost.plugins.gradle.services

import java.io.File
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object ChangelogService {
    fun getExtraCommits(extrasFile: File): List<GitService.GitCommit> {
        val extrasContents = extrasFile.readText().trim()

        return extrasContents.split("\n")
            .map {
                with(it.split(";", limit = 3)) {
                    GitService.GitCommit(
                        ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_LOCAL_DATE),
                        this[0], this[1], "", this[2].trimEnd(';'), true
                    )
                }
            }
    }
}
