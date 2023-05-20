package net.rentalhost.plugins.gradle.services

import net.rentalhost.plugins.gradle.ProjectTools
import org.gradle.api.Project
import java.io.File
import kotlin.random.Random

object GitService {
    private val commitSeparator = Random.nextBytes(8).toString()

    private val versionRegex = Regex("\\[(\\d+.\\d+.\\d+)]")

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

    fun parseVersions(message: String): Sequence<String> =
        versionRegex.findAll(message).map { it.groupValues[1] }

    class GitCommit(
        private val date: String,
        var tag: String,
        val box: String,
        val classReference: String,
        val message: String,
        val extraCommit: Boolean = false
    ) {
        companion object {
            fun createFrom(commit: String): GitCommit {
                val commitSplit = commit.split(commitSeparator)

                return GitCommit(
                    commitSplit[1],
                    with(commitSplit[2]) { substringAfter("tag: ", "").substringBefore(",") },
                    with(commitSplit[3]) { drop(1).substringBefore("]").lowercase() },
                    with(commitSplit[3]) { substringAfter("] ").substringBefore(":", "") },
                    with(commitSplit[3]) { substringAfter(": ", "").trim().trimEnd(';') }
                )
            }
        }

        fun isRecentlyImplemented(): Boolean =
            box == "added" &&
            message.startsWith("implemented (")

        fun isInspectionRelated(): Boolean =
            classReference.endsWith("Inspection")

        private fun getClassReferenceDashed(): String =
            StringService.dashCase(classReference)

        fun getClassReferenceUrl(pluginName: String): String =
            "https://github.com/hammer-tools/${pluginName}/wiki/Inspections#user-content-${getClassReferenceDashed()}"

        fun getMessagePrintable(): String =
            if (classReference == "") "$message;"
            else "**[$classReference]**: $message;"

        fun getTag(project: Project): String =
            if (tag == "") ProjectTools.prop(project, "pluginVersion")
            else tag.substringBefore("-")

        fun getTagDescription(project: Project): String =
            "[${getTag(project)}] - $date"

        fun versionInt(project: Project): Int = with(getTag(project)) {
            val (versionMajor, versionMinor, versionPatch) = this.split(".")

            return versionMajor.toInt() * 10000 + versionMinor.toInt() * 100 + versionPatch.toInt()
        }
    }
}
