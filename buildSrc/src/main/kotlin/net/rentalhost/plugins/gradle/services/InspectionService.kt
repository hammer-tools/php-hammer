package net.rentalhost.plugins.gradle.services

import org.gradle.api.Project
import java.io.File

object InspectionService {
    fun getInspection(project: Project): MutableList<LocalInspectionNode> {
        val plugin = XMLService.parse(File("${project.projectDir}/src/main/resources/META-INF/plugin.xml"))
        val pluginInspections = plugin.getElementsByTagName("localInspection")
        val pluginInspectionsMapped = mutableListOf<LocalInspectionNode>()

        (0 until pluginInspections.length).forEach { pluginInspection ->
            val pluginInspectionNode = pluginInspections.item(pluginInspection)

            with(pluginInspectionNode.attributes) {
                pluginInspectionsMapped.add(
                    LocalInspectionNode(
                        project,
                        getNamedItem("groupName").textContent,
                        getNamedItem("displayName").textContent,
                        getNamedItem("shortName").textContent,
                        getNamedItem("implementationClass").textContent,
                        getNamedItem("level").textContent
                    )
                )
            }
        }

        return pluginInspectionsMapped
    }

    class LocalInspectionNode(
        private val project: Project,
        val groupName: String,
        val displayName: String,
        val shortName: String,
        val implementationClass: String,
        val level: String
    ) {
        fun readDescription(): String =
            HTMLService.toMarkdown(File("${project.projectDir}/src/main/resources/inspectionDescriptions/$shortName.html"))
    }
}
