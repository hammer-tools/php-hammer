package net.rentalhost.plugins.php.hammer.services

import net.rentalhost.plugins.hammer.services.QuickFixService as BaseQuickFixService

class QuickFixService: BaseQuickFixService(ProjectService.instance) {
    companion object {
        val instance: QuickFixService = QuickFixService()
    }
}
