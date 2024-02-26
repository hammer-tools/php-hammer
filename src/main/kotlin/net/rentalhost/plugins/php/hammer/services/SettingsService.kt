package net.rentalhost.plugins.php.hammer.services

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime
import net.rentalhost.plugins.php.hammer.services.SettingsService.Companion.State as SettingsState

const val REVIEW_DISABLED: Long = -1L

@State(name = "PHPHammerState", storages = [Storage("php-hammer.state.xml")])
class SettingsService : PersistentStateComponent<SettingsState> {
    companion object {
        class State {
            var pluginFreshInstalled: Boolean = false
            var pluginVersion: String? = null

            var installedAt: Long? = null

            var countProjects: Long = 0
            var countInspections: Long = 0
            var countFixes: Long = 0

            var reviewAfter: Long = REVIEW_DISABLED
        }
    }

    fun getServiceInstance(): SettingsService =
        ApplicationManager.getApplication().getService(SettingsService::class.java)

    fun increaseFixes() {
        getServiceInstance().state.countFixes++
    }

    fun increaseInspections() {
        getServiceInstance().state.countInspections++
    }

    fun reviewRememberLater() {
        getServiceInstance().state.reviewAfter = ZonedDateTime.now().plusDays(30).toEpochSecond()
    }

    fun reviewDisable() {
        getServiceInstance().state.reviewAfter = REVIEW_DISABLED
    }

    fun isReviewTime(): Boolean {
        val reviewAt = getServiceInstance().state.reviewAfter

        if (reviewAt == REVIEW_DISABLED)
            return false

        val date = ZonedDateTime.now()

        return date.isAfter(ZonedDateTime.ofInstant(Instant.ofEpochSecond(reviewAt), ZoneOffset.UTC))
    }

    private var myState = SettingsState()

    override fun getState(): SettingsState = myState

    override fun loadState(state: SettingsState) {
        myState = state
    }
}
