package net.rentalhost.plugins.php.hammer.services

abstract class UrlService {
  abstract val homeUrl: String

  abstract val changelogUrl: String

  abstract val inspectionsUrl: String

  abstract val reviewsUrl: String
}
