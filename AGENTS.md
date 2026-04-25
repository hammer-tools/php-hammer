# AGENTS.md

PHP Hammer — IntelliJ/PhpStorm plugin that adds 57+ PHP inspections with quick-fixes.

## Creating a new inspection

Every inspection requires three files:

1. **Implementation** — Kotlin class extending `PhpInspection` in `src/main/kotlin/net/rentalhost/plugins/php/hammer/inspections/<category>/`
2. **Tests** — `*TestCase.kt` extending `TestCase` in `src/test/kotlin/net/rentalhost/plugins/php/hammer/inspections/<category>/`
3. **HTML description** — `src/main/resources/inspectionDescriptions/<ShortName>.html` shown in the IDE inspection popup

Always provide a **quick-fix** when possible.

## Testing

- **Base class**: `TestCase` (extends `BasePlatformTestCase`)
- **Naming**: test classes MUST be named `*TestCase.kt` — Gradle test filter is `**/*TestCase.class`
- **Test data path**: `src/test/resources/inspections/<category>/<InspectionName>/`
- **Fixture convention**:
  - `default.php` — main test fixture
  - `default.fixed.php` — expected result after all quick-fixes are applied (omit if no quick-fix exists)
  - `<optionName>.php` — fixtures for specific option configurations (e.g., `includeShortFunctionsDisabled.php`)
  - `<optionName>.fixed.php` — expected result after quick-fix for option-specific tests
  - PHP version variants: e.g., `default.fixed.php740.php`
- **`testInspection()`** derives the fixture path from the inspection class FQN (everything after `.hammer.`, dots replaced with `/`)
- **Shared helpers**: `src/test/resources/dummy/`

## Generated files — do NOT edit directly

The following are generated at build time. Edit their templates/sources instead:

| File                                     | Edit instead                                            |
| ---------------------------------------- | ------------------------------------------------------- |
| `src/main/resources/META-INF/plugin.xml` | `extras/plugin.stub`                                    |
| `CHANGELOG.md`                           | `extras/changelog.extras`                               |
| `wiki/Inspections.md`                    | Generated from `plugin.xml` + `inspectionDescriptions/` |

## Architecture

- Single package: `net.rentalhost.plugins.php.hammer`
- `src/main/kotlin/.../inspections/` — sub-packages by severity: `codeStyle`, `codeSmell`, `codeError`, `codeWarning`, `deadCode`, `deprecation`, `flowOptimization`
- `src/main/kotlin/.../extensions/psi/` — PSI extension functions; `extensions/kotlin/` — Kotlin stdlib extensions
- `src/main/kotlin/.../services/` — IntelliJ services (settings, sidebar, quick-fix, etc.)
- `src/main/java/.../forms/` — single Java GUI form (`SidebarForm.java` + `.form`)
- `buildSrc/` — custom Gradle plugin (`ProjectTools`) with code generation tasks

## Conventions

- Plugin ID: `net.rentalhost.plugins.php.hammer`
- PhpStorm target: `2026.1` (build `261`); defined in `gradle.properties`
- Commit format: `[%type] %scope: %summary` (see `ai-commit.json`)
- Kotlin stdlib is NOT bundled (`kotlin.stdlib.default.dependency = false`)

## Running tests

- **JDK requirement**: Gradle requires JVM 17+. The system default may be JVM 8, so set `JAVA_HOME` to the JBR 21 bundled with PhpStorm:
  ```powershell
  $env:JAVA_HOME = "$env:USERPROFILE\.jdks\jbr-21.0.10"
  ./gradlew test --tests "*<InspectionName>TestCase"
  ```
- **Always run targeted tests**, never `gradlew test` without a `--tests` filter — there are 57+ inspections and global test runs take too long.
- Test class naming filter: `*TestCase` (e.g., `--tests "*OverrideIllegalInspectionTestCase"`)
