package net.rentalhost.plugins.gradle.services

import java.io.File
import kotlin.random.Random

object GitService {
    private val commitSeparator = Random.nextBytes(8).toString()

    fun getCommits(workingDirectory: File): List<GitCommit> {
        val processBuilder = ProcessBuilder(
            "git", "log", "--abbrev=8",
            "--format=\"%h${commitSeparator}%as${commitSeparator}%D${commitSeparator}%s\""
        )

        processBuilder.redirectOutput(ProcessBuilder.Redirect.PIPE)
        processBuilder.redirectError()
        processBuilder.directory(workingDirectory)

        val processHandle = processBuilder.start()
        val processResult = processHandle.inputStream.bufferedReader().readText()

        processHandle.waitFor()

        var currentTag = ""

        return processResult.split("\n")
            .filter { it != "" }
            .map {
                val commit = GitCommit.createFrom(it)

                if (commit.tag == "") commit.tag = currentTag
                else currentTag = commit.tag

                commit
            }
    }

    class GitCommit(
        private val date: String,
        var tag: String,
        val box: String,
        val classReference: String,
        private val message: String
    ) {
        companion object {
            fun createFrom(commit: String): GitCommit {
                val commitSplit = commit.split(commitSeparator)

                return GitCommit(
                    commitSplit[1],
                    with(commitSplit[2]) { substringAfter("tag: ", "").substringBefore(",") },
                    with(commitSplit[3]) { drop(1).substringBefore("]").toLowerCase() },
                    with(commitSplit[3]) { substringAfter("] ").substringBefore(":", "") },
                    with(commitSplit[3]) { substringAfter(": ", "").trim().trimEnd(';') }
                )
            }
        }

        fun isInspectionRelated(): Boolean =
            classReference.endsWith("Inspection")

        private fun getClassReferenceDashed(): String =
            StringService.dashCase(classReference)

        fun getClassReferenceUrl(): String =
            "https://github.com/hammer-tools/php-hammer/wiki/Inspections#user-content-${getClassReferenceDashed()}"

        fun getMessagePrintable(): String =
            "**[$classReference]**: $message;"

        fun getTagDescription(): String {
            if (tag == "")
                return "[Unreleased]"

            return "[${tag.substringBefore("-")}] - $date"
        }
    }
}
