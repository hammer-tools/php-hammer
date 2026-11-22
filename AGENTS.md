# AGENTS.md

PHP Hammer is a PhpStorm plugin with PHP inspections and quick fixes written in Kotlin. Its plugin
ID is `net.rentalhost.plugins.php.hammer`. Follow the commit format in `ai-commit.json`.

## Upgrading PhpStorm

1. Confirm the stable release and its exact build on JetBrains' download pages. Check the
   [SDK build number ranges](https://plugins.jetbrains.com/docs/intellij/build-number-ranges.html)
   for the required Java version. Align the toolchain in `build.gradle.kts`, the JDK in
   `.idea/misc.xml`, and the GitHub Actions workflow.
2. Update the fields in `gradle.properties` independently. `platformVersion` selects the SDK for
   compilation and tests. `pluginBuildSince` sets the minimum IDE build for installation.
   `pluginVersion` sets the version in the plugin descriptor and the ZIP filename. Use three
   components for the plugin version because the changelog generator parses major, minor, and patch.
3. Check stable dependency releases at their official sources. Review the IntelliJ Platform Gradle
   Plugin, Kotlin Gradle Plugin, Plugin Verifier, ZIP Signer, and test libraries in
   `build.gradle.kts`, jsoup in `buildSrc/build.gradle.kts`, Kotlin in `.idea/kotlinc.xml`, and the
   actions in `.github/workflows/gradle.yml`. Use the PHP plugin and Kotlin standard library bundled
   with the IDE.
4. Keep `gradleVersion` and the Gradle Wrapper in sync. Run `wrapper` with `--gradle-version` and
   the official distribution checksum in `--gradle-distribution-sha256-sum`. Repeat the task with
   the updated wrapper so it regenerates both scripts and the JAR. Check the wrapper properties,
   scripts, and official JAR checksum.
5. Review the
   [platform API changes](https://plugins.jetbrains.com/docs/intellij/api-changes-list.html).
   Resolve compile and test regressions while preserving fixture expectations. Review the sandbox
   and log path in `.run/Run Plugin.run.xml` when the IDE distribution changes. Configure disabled
   plugins in `sandbox/disabled_plugins.txt`.
6. Verify the ZIP against the target build. The Gradle configuration uses `ides { current() }` to
   verify against the configured SDK. If the minimum supported build belongs to an earlier release
   series, add that IDE to verification and run the test suite on it before declaring support.

## Tests and packaging

Point `JAVA_HOME` to a JDK or JBR for the target IDE series. Run the commands from the project root.
Use `./gradlew` on Linux.

```powershell
.\gradlew.bat test --tests '*OverrideIllegalInspectionTestCase'
.\gradlew.bat test --tests '*TestCase' check buildPlugin verifyPlugin
```

Use a specific test filter for focused changes and `*TestCase` for the full suite on SDK upgrades
and releases. Keep fixtures under version control and check the totals in `build/test-results/test`.
If SDK files disappear from the shared Gradle cache during verification, retry with a dedicated
`GRADLE_USER_HOME` under an ignored directory such as `.gradle/upgrade-validation`.

- Confirm zero failures, errors, and skipped tests in `build/reports/tests/test/index.html`.
- Confirm the target IDE build and the compatibility verdict in
  `build/reports/pluginVerifier/<ide-build>/report.html`.
- Open the plugin JAR inside the new ZIP in `build/distributions`. Check `<version>` and
  `<idea-version since-build="…">` in `META-INF/plugin.xml`. Earlier ZIPs can remain in the output
  directory, so use the one that matches the release version.

The automated tests run the IDE platform in test mode. Plugin Verifier checks the packaged ZIP.
Report manual installation and remote CI results separately when those checks are performed.

## Preparing a release

1. Review the changes since the previous release tag. Record release-level changes in
   `extras/changelog.extras` using the existing version, category, and message format. Run
   `generateChangelog` and review the new section alongside the previous release.
2. Format the edited files, run the full release validation above, and inspect the packaged
   descriptor. Calculate SHA-256 from the exact ZIP that passed verification.
3. Keep the release notes and checksum with the ZIP. The Git tag should match `pluginVersion` and
   point to the commit containing the validated source. Tagging and publishing are separate release
   steps.

## PhpStorm 2026.3 migration reference

- Target SDK PhpStorm 2026.3, build `263.7307.215`, with Java 25. Plugin version `2026.3.0` requires
  build `263`. Build `262.10968.76` is PhpStorm 2026.2.3 and falls below that minimum.
- Gradle 9.9.2, IntelliJ Platform Gradle Plugin 2.20.1, Kotlin 2.4.21, jsoup 1.24.1, Plugin Verifier
  1.411, and ZIP Signer 0.1.45. The test framework uses JUnit 4.13.2 and OpenTest4J 1.3.0.
- The migration removed the obsolete `setupDependencies` task, configured the sandbox through the
  Gradle plugin API, and switched notification links to native actions. `PluginUpdateService` reads
  the version through `PluginDetailsService`. Kotlin uses `JvmDefaultMode.NO_COMPATIBILITY` to avoid
  generating compatibility methods that forward to obsolete IDE APIs.
- The first test run exposed four failures covering `#[Override]` hierarchy checks, promoted
  `readonly` parameters with PHPDoc, and path separator quick fixes in PHP strings. The fixes
  preserved the existing fixtures. The final migration run passed 105 tests across 57 classes, and
  Plugin Verifier marked `php-hammer-2026.3.0.zip` compatible.

## Inspections and generated files

- Implement inspections under
  `src/main/kotlin/net/rentalhost/plugins/php/hammer/inspections/<category>/` by extending
  `PhpInspection`. Reuse the PSI extensions in `extensions/psi` and existing plugin services.
  Provide a quick fix when possible.
- Add a `*TestCase.kt` class under the matching category in `src/test/kotlin`, extending the
  project's `TestCase`, and add an HTML description under
  `src/main/resources/inspectionDescriptions`.
- Store fixtures in `src/test/resources/inspections/<category>/<InspectionName>/`. Use `default.php`
  and `default.fixed.php` when testing a quick fix. Use option-specific names and a suffix such as
  `.fixed.php740.php` when results vary by PHP version. Shared fixtures live in
  `src/test/resources/dummy/`.
- The `buildSrc` task `generatePluginXML` updates `src/main/resources/META-INF/plugin.xml`. Set the
  version and minimum build in `gradle.properties` and edit `extras/plugin.stub` for descriptor
  structure changes.
- `CHANGELOG.md` is generated from Git history and `extras/changelog.extras`. The
  `generateDocumentation` task writes `wiki/Inspections.md` from the descriptor and inspection
  descriptions. The aggregate `build` task invokes these generators. Use `check`, `buildPlugin`, and
  `verifyPlugin` to validate and package without writing to the wiki.
