package net.rentalhost.plugins.services

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
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

            var countProjects: Int = 0
            var countInspections: Int = 0
            var countFixes: Int = 0
        }
    }

    private var myState = SettingsState()

    override fun getState(): SettingsState = myState

    override fun loadState(state: SettingsState) {
        myState = state
    }
}
