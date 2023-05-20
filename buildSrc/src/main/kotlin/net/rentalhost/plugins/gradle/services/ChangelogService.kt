package net.rentalhost.plugins.gradle.services

import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object ChangelogService {
    fun getExtraCommits(extras: String): List<GitService.GitCommit> {
        val extrasContents = extras.trim()

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
