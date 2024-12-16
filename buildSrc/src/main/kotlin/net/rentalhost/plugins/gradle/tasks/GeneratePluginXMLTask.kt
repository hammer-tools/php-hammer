package net.rentalhost.plugins.gradle.tasks

import net.rentalhost.plugins.gradle.ProjectTools
import net.rentalhost.plugins.gradle.services.FileService
import net.rentalhost.plugins.gradle.services.InspectionService
import org.gradle.api.Project
import org.jsoup.parser.Parser
import java.io.File

internal class GeneratePluginXMLTask : ProjectTools.ProjectTask() {
    private val copyProperties = listOf("pluginId", "pluginVersion", "pluginBuildSince")

    override fun apply(project: Project) {
        project.task("generatePluginXML") {
            group = groupName
            description = "Generate project plugin.xml meta file."

            doLast { generatePluginXML(project) }
        }
    }

    private fun generatePluginXML(project: Project) {
        val pluginStubFile = File("${project.projectDir}/extras/plugin.stub")

        if (!pluginStubFile.exists())
            return

        var pluginXMLContents = FileService.read(pluginStubFile.path)
        val pluginTitle = ProjectTools.prop(project, "pluginTitle")
        val pluginTitleFull = Parser.unescapeEntities(ProjectTools.prop(project, "pluginTitleFull"), false)
        val pluginInspections = InspectionService.getInspection(project)

        for (copyProperty in copyProperties) {
            pluginXMLContents = pluginXMLContents.replace("\$$copyProperty", ProjectTools.prop(project, copyProperty))
        }

        pluginXMLContents = pluginXMLContents.replace(
            "\$pluginDescription",
            FileService.read("${project.projectDir}/extras/pluginDescription.html")
                .replace("\$inspectionsCount", pluginInspections.size.toString())
                .trim()
        )


        pluginXMLContents = pluginXMLContents.replace(
            "\$pluginInspections",
            pluginInspections
                .groupBy { it.groupName }
                .map { it ->
                    var response = "<!-- ${it.value.first().groupName} -->\n"

                    it.value
                        .sortedBy { it.displayName }
                        .forEach {
                            response +=
                                "<localInspection language=\"PHP\"\n" +
                                        "                 groupPath=\"PHP,$pluginTitleFull\"\n" +
                                        "                 groupName=\"${it.groupName}\"\n" +
                                        "                 enabledByDefault=\"true\"\n" +
                                        "                 displayName=\"${it.displayName}\"\n" +
                                        "                 level=\"${it.level}\"\n" +
                                        "                 shortName=\"${it.shortName}\"\n" +
                                        (if (it.presentationClass == null) ""
                                        else "                 presentation=\"${it.presentationClass}\"\n") +
                                        "                 implementationClass=\"${it.implementationClass}\" />\n\n"
                        }

                    return@map response.prependIndent("        ").trimStart()
                }
                .joinToString("")
                .trimEnd()
                .replace(Regex("(?<=\n)\\s+(?=\n)"), "")
        )

        pluginXMLContents = pluginXMLContents.replace("\$pluginTitle", pluginTitle)

        File("${project.projectDir}/src/main/resources/META-INF/plugin.xml")
            .writeText(pluginXMLContents)
    }
}
