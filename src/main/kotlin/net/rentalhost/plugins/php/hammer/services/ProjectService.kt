package net.rentalhost.plugins.php.hammer.services

class ProjectService {
  companion object {
    var instance: ProjectService = ProjectService()
  }

  val id: String = "net.rentalhost.plugins.php.hammer"

  val name: String = "PHP Hammer"

  val notificationGroup: String = "net.rentalhost.plugins.php.hammer.notifications"

  val sentryDsn: String = "https://0046f19fa48247e198f0a5cb21afc0db@o55698.ingest.sentry.io/6612594"

  val urls: UrlService = object : UrlService() {
    override val homeUrl: String = "https://github.com/hammer-tools/php-hammer"

    override val changelogUrl: String = "https://github.com/hammer-tools/php-hammer/blob/master/CHANGELOG.md"

    override val inspectionsUrl: String = "https://github.com/hammer-tools/php-hammer/wiki/Inspections"

    override val reviewsUrl: String = "https://plugins.jetbrains.com/plugin/19515--php-hammer/reviews/new"
  }

  val settings: SettingsService = SettingsService()
}
