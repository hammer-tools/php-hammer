package net.rentalhost.plugins.services

import com.intellij.ide.BrowserUtil
import com.intellij.notification.Notification
import com.intellij.notification.NotificationAction
import com.intellij.notification.NotificationListener.UrlOpeningListener
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.ModalityState

object NotificationService {
    fun notify(
        groupId: String,
        content: String,
        actions: List<Triple<String, String, String>>? = null
    ) {
        val notification = Notification(groupId, "", NotificationType.INFORMATION)

        @Suppress("DEPRECATION")
        notification.setListener(UrlOpeningListener(false))

        var finalContent = content

        if (actions != null) {
            for ((name, title, url) in actions) {
                finalContent = finalContent.replace("\$${name}", "<a href=\"${url}\">${title}</a>")

                notification.addAction(NotificationAction.create(title.replaceFirstChar(Char::titlecase)) { _ -> BrowserUtil.open(url) })
            }
        }

        notification.setContent(finalContent)

        ApplicationManager.getApplication().invokeLater({ Notifications.Bus.notify(notification) }, ModalityState.NON_MODAL)
    }
}
