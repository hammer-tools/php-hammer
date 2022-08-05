package net.rentalhost.plugins.php.hammer.services

import net.rentalhost.plugins.hammer.services.UrlService
import net.rentalhost.plugins.hammer.services.ProjectService as BaseProjectService

class ProjectService: BaseProjectService() {
    companion object {
        var instance: ProjectService = ProjectService()
    }

    override val name: String = "PHP Hammer"

    override val sentryDsn: String = "https://0046f19fa48247e198f0a5cb21afc0db@o55698.ingest.sentry.io/6612594"

    override val urls: UrlService = object: UrlService() {
        override val homeUrl: String = "https://github.com/hammer-tools/php-hammer"

        override val changelogUrl: String = "https://github.com/hammer-tools/php-hammer/blob/master/CHANGELOG.md"

        override val freemiumUrl: String = "https://github.com/hammer-tools/php-hammer/wiki/Freemium"

        override val inspectionsUrl: String = "https://github.com/hammer-tools/php-hammer/wiki/Inspections"

        override val reviewsUrl: String = "https://plugins.jetbrains.com/plugin/19515--php-hammer/reviews/new"
    }

    override val settings: SettingsService = SettingsService()
}
