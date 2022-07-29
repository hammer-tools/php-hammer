package net.rentalhost.plugins.gradle.services

import java.io.File

object ChangelogService {
    fun getExtraCommits(extrasFile: File): List<GitService.GitCommit> {
        val extrasContents = extrasFile.readText().trim()

        return extrasContents.split("\n")
            .map {
                with(it.split(";", limit = 3)) {
                    GitService.GitCommit("", this[0], this[1], "", this[2].trimEnd(';'))
                }
            }
    }
}
