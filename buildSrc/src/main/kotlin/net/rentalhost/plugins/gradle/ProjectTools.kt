package net.rentalhost.plugins.gradle

import net.rentalhost.plugins.gradle.tasks.GenerateChangelogTask
import net.rentalhost.plugins.gradle.tasks.GenerateDocumentationTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class ProjectTools: Plugin<Project> {
    override fun apply(project: Project) {
        GenerateDocumentationTask().apply(project)
        GenerateChangelogTask().apply(project)
    }

    internal abstract class ProjectTask {
        val groupName: String = "project-tools"

        abstract fun apply(project: Project)
    }
}
