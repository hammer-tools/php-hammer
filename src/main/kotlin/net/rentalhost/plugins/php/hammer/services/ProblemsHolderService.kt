package net.rentalhost.plugins.php.hammer.services

import net.rentalhost.plugins.hammer.services.ProblemsHolderService

class ProblemsHolderService: ProblemsHolderService(ProjectService.instance) {
    companion object {
        val instance: ProblemsHolderService = ProblemsHolderService()
    }
}
