package net.rentalhost.plugins.php.hammer.services

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import net.rentalhost.plugins.hammer.services.SettingsService as BaseSettingsService

@State(name = "PHPHammerState", storages = [Storage("php-hammer.state.xml")])
class SettingsService: BaseSettingsService(), PersistentStateComponent<BaseSettingsService.Companion.State> {
    override fun getServiceInstance(): SettingsService =
        ApplicationManager.getApplication().getService(SettingsService::class.java)
}
