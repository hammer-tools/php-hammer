package net.rentalhost.plugins.gradle.tasks

import net.rentalhost.plugins.gradle.ProjectTools
import net.rentalhost.plugins.gradle.services.InspectionService
import net.rentalhost.plugins.gradle.services.StringService
import org.gradle.api.Project
import java.io.File

internal class GenerateDocumentationTask : ProjectTools.ProjectTask() {
    override fun apply(project: Project) {
        project.task("generateDocumentation") {
            group = groupName
            description = "Generate project documentation based on plugin.xml file."

            doLast { generateDocumentation(project) }
        }
    }

    private fun generateDocumentation(project: Project) {
        val pluginInspections = InspectionService.getInspection(project)

        val outputInspectionsFile = File("${project.projectDir}/wiki/Inspections.md")
        var outputInspectionsContent = "Currently, **${pluginInspections.size} inspections** have been implemented.\n\n"

        pluginInspections
            .sortedBy { it.displayName }
            .groupBy { it.groupName }
            .forEach {
                outputInspectionsContent += "# ${it.key}\n\n"

                it.value.forEach { localInspection ->
                    outputInspectionsContent += "<a id=\"${StringService.dashCase(localInspection.shortName)}\"></a>\n\n"
                    outputInspectionsContent += "## ${localInspection.displayName}\n\n"
                    outputInspectionsContent += "${localInspection.readDescription()}\n\n"
                }
            }

        outputInspectionsFile.writeText(outputInspectionsContent)
    }
}
