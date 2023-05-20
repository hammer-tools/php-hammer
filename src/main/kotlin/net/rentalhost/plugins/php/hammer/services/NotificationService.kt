package net.rentalhost.plugins.php.hammer.services

import com.intellij.ide.BrowserUtil
import com.intellij.notification.Notification
import com.intellij.notification.NotificationAction
import com.intellij.notification.NotificationListener
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnActionEvent

object NotificationService {
    fun notify(
        groupId: String,
        content: String,
        actions: List<NotificationItem>? = null
    ) {
        val notification = Notification(groupId, "", NotificationType.INFORMATION)

        @Suppress("DEPRECATION")
        notification.setListener(NotificationListener.UrlOpeningListener(false))

        var finalContent = content

        if (actions != null) {
            for ((name, documentTitle, buttonTitle, url, callback) in actions) {
                if (name != null) {
                    finalContent = finalContent.replace("\$${name}", "<a href=\"${url}\">${documentTitle}</a>")
                }

                if (buttonTitle != null) {
                    val buttonTitleNormalized = buttonTitle.replaceFirstChar(Char::titlecase)

                    notification.addAction(object: NotificationAction(buttonTitleNormalized) {
                        override fun actionPerformed(e: AnActionEvent, notification: Notification) {
                            if (url != null) {
                                BrowserUtil.open(url)
                            }

                            callback?.invoke(notification)
                        }
                    })
                }
            }
        }

        notification.collapseDirection = Notification.CollapseActionsDirection.KEEP_LEFTMOST
        notification.setContent(finalContent)
        notification.notify(null)
    }

    data class NotificationItem(
        val name: String?,
        val documentTitle: String?,
        val buttonTitle: String?,
        val url: String?,
        val callback: ((Notification) -> Unit)?
    ) {
        constructor(name: String, documentTitle: String, buttonTitle: String, url: String): this(name, documentTitle, buttonTitle, url, null)
        constructor(name: String, documentTitle: String, url: String, callback: (Notification) -> Unit): this(name, documentTitle, documentTitle, url, callback)
        constructor(name: String, documentTitle: String, url: String): this(name, documentTitle, documentTitle, url, null)
        constructor(buttonTitle: String, callback: (Notification) -> Unit): this(null, null, buttonTitle, null, callback)

        fun withoutActionButton(): NotificationItem =
            copy(buttonTitle = null)
    }
}
