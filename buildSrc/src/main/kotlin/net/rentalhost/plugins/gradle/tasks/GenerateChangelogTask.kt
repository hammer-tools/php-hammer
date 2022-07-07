package net.rentalhost.plugins.gradle.tasks

import net.rentalhost.plugins.gradle.ProjectTools
import net.rentalhost.plugins.gradle.services.GitService
import net.rentalhost.plugins.gradle.services.GitService.GitCommit
import org.gradle.api.Project
import org.gradle.configurationcache.extensions.capitalized
import java.io.File

internal class GenerateChangelogTask: ProjectTools.ProjectTask() {
    private val boxNames = listOf("added", "changed", "fixed", "removed")

    override fun apply(project: Project) {
        project.task("generateChangelog") {
            group = groupName
            description = "Generate project changelog based on git commits."

            doLast { generateChangelog(project) }
        }
    }

    private fun generateChangelog(project: Project) {
        var changelogResult = "# Changelog\n\n" +
                              "All notable changes to this project will be documented in this file.\n\n" +
                              "The format is based on [**Keep a Changelog**](https://keepachangelog.com/en/1.0.0/),\n" +
                              "and this project adheres to [**Semantic Versioning**](https://semver.org/spec/v2.0.0.html).\n\n"

        val commits = GitService.getCommits(project.projectDir)

        val urlReferences = mutableMapOf<String, String>()

        commits.groupBy { it.tag }
            .forEach {
                with(it.value) {
                    val commitBoxes = boxNames.associateWith { mutableListOf<GitCommit>() }

                    val recentlyAdded = mutableListOf<String>()
                    val recentlyRemoved = mutableListOf<String>()

                    reversed()
                        .filter { commitBoxes.containsKey(it.box) }
                        .filter { it.isInspectionRelated() }
                        .also { forEach { if (it.box == "removed") recentlyRemoved.add(it.classReference) } }
                        .forEach commitLoop@{
                            val commitBox = commitBoxes[it.box] ?: return@commitLoop

                            if (recentlyAdded.contains(it.classReference) ||
                                recentlyRemoved.contains(it.classReference)) {
                                return@commitLoop
                            }
                            else if (it.box == "added") {
                                recentlyAdded.add(it.classReference)
                            }

                            commitBox.add(it)
                        }

                    if (commitBoxes.all { it.value.isEmpty() })
                        return@with

                    if (it.key != "Unreleased") {
                        urlReferences[it.key] = "https://github.com/hammer-tools/php-hammer/releases/tag/${it.key}"
                    }

                    changelogResult += "## ${first().getTagDescription()}\n\n"

                    for ((key, value) in commitBoxes) {
                        if (value.isEmpty())
                            continue

                        changelogResult += "### ${key.capitalized()}\n\n"

                        value.sortedBy { it.classReference }.forEach {
                            urlReferences[it.classReference] = it.getClassReferenceUrl()
                            changelogResult += "- ${it.getMessagePrintable()}\n"
                        }

                        changelogResult += "\n"
                    }
                }
            }

        urlReferences.forEach { (reference, url) -> changelogResult += "[$reference]: $url\n\n" }

        File("${project.projectDir}/CHANGELOG.md").writeText(changelogResult)
    }
}
