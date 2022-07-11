package net.rentalhost.plugins.services

import com.intellij.notification.BrowseNotificationAction
import com.intellij.notification.Notification
import com.intellij.notification.NotificationListener
import com.intellij.notification.NotificationType

object NotificationService {
    fun notify(
        groupId: String,
        content: String,
        actions: List<Triple<String, String, String>>? = null
    ) {
        val notification = Notification(groupId, "", NotificationType.INFORMATION)

        @Suppress("DEPRECATION")
        notification.setListener(NotificationListener.UrlOpeningListener(false))

        var finalContent = content

        if (actions != null) {
            for ((name, title, url) in actions) {
                finalContent = finalContent.replace("\$${name}", "<a href=\"${url}\">${title}</a>")

                notification.addAction(BrowseNotificationAction(title.replaceFirstChar(Char::titlecase), url))
            }
        }

        notification.setContent(finalContent)
        notification.notify(null)
    }
}
