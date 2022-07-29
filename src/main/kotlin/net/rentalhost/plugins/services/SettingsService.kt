package net.rentalhost.plugins.services

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime
import net.rentalhost.plugins.services.SettingsService.Companion.State as SettingsState

@State(name = "PHPHammerState", storages = [Storage("php-hammer.state.xml")])
class SettingsService: PersistentStateComponent<SettingsState> {
    companion object {
        fun getInstance(): SettingsService =
            ApplicationManager.getApplication().getService(SettingsService::class.java)

        fun increaseFixes() {
            getInstance().state.countFixes++
        }

        fun increaseInspections() {
            getInstance().state.countInspections++
        }

        class State {
            var pluginFreshInstalled: Boolean = false
            var pluginVersion: String? = null

            var installedAt: Long? = null

            var countProjects: Long = 0
            var countInspections: Long = 0
            var countFixes: Long = 0

            var reviewAfter: Long = Review.DISABLED_STATE
        }
    }

    object Review {
        const val DISABLED_STATE: Long = -1L

        fun isReviewTime(): Boolean {
            val reviewAt = getInstance().state.reviewAfter

            if (reviewAt == DISABLED_STATE)
                return false

            val date = ZonedDateTime.now()

            return date.isAfter(ZonedDateTime.ofInstant(Instant.ofEpochSecond(reviewAt), ZoneOffset.UTC))
        }

        fun rememberReviewNotificationLater() {
            getInstance().state.reviewAfter = ZonedDateTime.now().plusDays(30).toEpochSecond()
        }

        fun disableReviewNotification() {
            getInstance().state.reviewAfter = DISABLED_STATE
        }
    }

    private var myState = SettingsState()

    override fun getState(): SettingsState = myState

    override fun loadState(state: SettingsState) {
        myState = state
    }
}
