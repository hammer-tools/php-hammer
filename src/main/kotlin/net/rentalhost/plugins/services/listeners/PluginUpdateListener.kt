package net.rentalhost.plugins.services.listeners

import com.intellij.ide.plugins.IdeaPluginDescriptor
import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.openapi.extensions.PluginId
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener
import net.rentalhost.plugins.services.NotificationService
import net.rentalhost.plugins.services.ResourceService
import net.rentalhost.plugins.services.SettingsService

internal class PluginUpdateListener: ProjectManagerListener {
    private val plugin: IdeaPluginDescriptor = PluginManagerCore.getPlugin(PluginId.findId("net.rentalhost.plugins.php.hammer"))!!

    private val tripleHome = Triple("home", "home", "https://github.com/hammer-tools/php-hammer")
    private val tripleChangelog = Triple("changelog", "changelog", "https://github.com/hammer-tools/php-hammer/blob/master/CHANGELOG.md")
    private val tripleFreemium = Triple("freemium", "freemium", "https://github.com/hammer-tools/php-hammer/wiki/Freemium")
    private val tripleInspections = Triple("inspections", "inspections", "https://github.com/hammer-tools/php-hammer/wiki/Inspections")

    override fun projectOpened(project: Project) {
        with(SettingsService.getInstance().state) {
            countProjects++

            if (!pluginFreshInstalled) {
                pluginFreshInstalled = true

                notifyInstall()
            }
            else if (pluginVersion != plugin.version) {
                notifyUpdate(pluginVersion, plugin.version)
            }

            pluginVersion = plugin.version
        }
    }

    private fun notifyInstall() {
        NotificationService.notify(
            "net.rentalhost.plugins.notification.INSTALLED",
            ResourceService.read("/plugin/welcome.html"),
            listOf(tripleHome, tripleInspections, tripleFreemium)
        )
    }

    private fun notifyUpdate(versionBefore: String?, versionAfter: String) = NotificationService.notify(
        "net.rentalhost.plugins.notification.UPDATED",
        ResourceService.read("/plugin/news.html")
            .replace("\$beforeNote", if (versionBefore != null) ", replacing the previously installed version (was $versionBefore)" else "")
            .replace("\$pluginVersion", versionAfter),
        listOf(tripleChangelog, tripleFreemium)
    )
}
