package net.rentalhost.plugins.gradle

import net.rentalhost.plugins.gradle.tasks.GenerateDocumentationTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class ProjectTools: Plugin<Project> {
    override fun apply(project: Project) {
        GenerateDocumentationTask().apply(project)
    }

    interface ProjectTask {
        fun apply(project: Project)
    }
}
