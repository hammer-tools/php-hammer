package net.rentalhost.plugins.php.hammer.services

import com.intellij.ide.plugins.IdeaPluginDescriptor
import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.notification.Notification
import com.intellij.openapi.extensions.PluginId
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import java.time.ZonedDateTime

class PluginUpdateService : ProjectActivity {
  private val plugin: IdeaPluginDescriptor = PluginManagerCore.getPlugin(PluginId.findId(ProjectService.instance.id))!!

  private val tripleHome = NotificationService.NotificationItem("home", "project home", "home", ProjectService.instance.urls.homeUrl)
  private val tripleChangelog = NotificationService.NotificationItem("changelog", "changelog", ProjectService.instance.urls.changelogUrl)
  private val tripleInspections = NotificationService.NotificationItem("inspections", "inspections", ProjectService.instance.urls.inspectionsUrl)

  private val tripleReviewNow = NotificationService.NotificationItem("review", "review", ProjectService.instance.urls.reviewsUrl) { notification -> closeReview(notification) }
  private val tripleReviewLater = NotificationService.NotificationItem("Remember later") { notification -> closeReview(notification, true) }
  private val tripleReviewNever = NotificationService.NotificationItem("Never ask again") { notification -> closeReview(notification) }
  private val tripleReviewHome = tripleHome.withoutActionButton()

  private fun closeReview(notification: Notification, disableReview: Boolean = false) {
    notification.expire()

    if (disableReview) {
      ProjectService.instance.settings.reviewDisable()
    }
  }

  private fun notifyInstall() {
    NotificationService.notify(
      "${ProjectService.instance.notificationGroup}.PLUGIN_RELATED",
      ResourceService.read("/plugin/welcome.html"),
      listOf(tripleHome, tripleInspections)
    )
  }

  private fun notifyUpdate(versionBefore: String?, versionAfter: String) = NotificationService.notify(
    "${ProjectService.instance.notificationGroup}.PLUGIN_RELATED",
    ResourceService.read("/plugin/news.html")
      .replace(
        "\$beforeNote",
        if (versionBefore != null) ", replacing the previous installation (was $versionBefore)"
        else ""
      )
      .replace("\$pluginVersion", versionAfter),
    listOf(tripleChangelog)
  )

  private fun notifyReview() {
    ProjectService.instance.settings.reviewRememberLater()

    NotificationService.notify(
      "${ProjectService.instance.notificationGroup}.PLUGIN_RELATED",
      ResourceService.read("/plugin/review.html"),
      listOf(tripleReviewNow, tripleReviewLater, tripleReviewNever, tripleReviewHome)
    )
  }

  override suspend fun execute(project: Project) {
    with(ProjectService.instance.settings.getServiceInstance().state) {
      countProjects++

      if (!pluginFreshInstalled) {
        pluginFreshInstalled = true

        notifyInstall()
      }
      else if (pluginVersion != plugin.version) {
        notifyUpdate(pluginVersion, plugin.version)
      }
      else if (ProjectService.instance.settings.isReviewTime()) {
        notifyReview()
      }

      pluginVersion = plugin.version

      if (installedAt == null) {
        val date = ZonedDateTime.now()

        installedAt = date.toEpochSecond()
        reviewAfter = date.plusDays(30).toEpochSecond()
      }
    }
  }
}
