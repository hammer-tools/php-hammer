package net.rentalhost.plugins.gradle

import net.rentalhost.plugins.gradle.services.HTMLService
import net.rentalhost.plugins.gradle.services.XMLService
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

class ProjectTools: Plugin<Project> {
    override fun apply(project: Project) {
        project.task("generateDocumentation") {
            group = "project tools"
            description = "Generate project documentation based on plugin.xml file."

            doLast { generateDocumentation(project) }
        }
    }

    private fun generateDocumentation(project: Project) {
        val plugin = XMLService.parse(File("${project.projectDir}/src/main/resources/META-INF/plugin.xml"))
        val pluginInspections = plugin.getElementsByTagName("localInspection")
        val pluginInspectionsMapped = mutableListOf<LocalInspectionNode>()

        for (pluginInspection in 0 .. pluginInspections.length - 1) {
            val pluginInspectionNode = pluginInspections.item(pluginInspection)

            with(pluginInspectionNode.attributes) {
                pluginInspectionsMapped.add(
                    LocalInspectionNode(
                        project,
                        getNamedItem("groupName").textContent,
                        getNamedItem("displayName").textContent,
                        getNamedItem("shortName").textContent,
                        getNamedItem("implementationClass").textContent
                    )
                )
            }
        }

        val outputInspectionsFile = File("${project.projectDir}/wiki/Inspections.md")
        var outputInspectionsContent = "Currently, **${pluginInspections.length} inspections** have been implemented.\n\n"

        pluginInspectionsMapped
            .sortedBy { it.description }
            .groupBy { it.group }
            .forEach {
                outputInspectionsContent += "# ${it.key}\n\n"

                it.value.forEach {
                    outputInspectionsContent += "## ${it.description}\n\n"
                    outputInspectionsContent += "${it.readDescription()}\n\n"
                }
            }

        outputInspectionsFile.writeText(outputInspectionsContent)
    }

    private class LocalInspectionNode(
        private val project: Project,
        val group: String,
        val description: String,
        val shortName: String,
        val implementationClass: String
    ) {
        fun readDescription(): String =
            HTMLService.toMarkdown(File("${project.projectDir}/src/main/resources/inspectionDescriptions/$shortName.html"))
    }
}
