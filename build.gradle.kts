import org.jetbrains.intellij.platform.gradle.TestFrameworkType
import org.jetbrains.kotlin.gradle.dsl.JvmDefaultMode

fun prop(key: String) = project.findProperty(key).toString()

plugins {
    id("java")
    id("org.jetbrains.intellij.platform") version "2.20.1"
    id("org.jetbrains.kotlin.jvm") version "2.4.21"
}

dependencies {
    intellijPlatform {
        phpstorm(prop("platformVersion"))

        bundledPlugin("com.jetbrains.php")

        pluginVerifier("1.411")
        zipSigner("0.1.45")

        testFramework(TestFrameworkType.Platform)
    }

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.opentest4j:opentest4j:1.3.0")
}

group = prop("pluginId")
version = prop("pluginVersion")

repositories {
    mavenCentral()

    intellijPlatform {
        defaultRepositories()
    }
}

apply {
    plugin(net.rentalhost.plugins.gradle.ProjectTools::class)
}

kotlin {
    jvmToolchain(25)
    compilerOptions.jvmDefault.set(JvmDefaultMode.NO_COMPATIBILITY)
}

tasks {
    build {
        dependsOn("generatePluginXML")
        dependsOn("generateChangelog")
        dependsOn("generateDocumentation")
    }

    test {
        isScanForTestClasses = false

        include("**/*TestCase.class")

        filter {
            excludeTestsMatching("net.rentalhost.plugins.php.hammer.TestCase")
        }

        systemProperty("idea.split.test.logs", "true")
    }

    wrapper {
        gradleVersion = prop("gradleVersion")
    }

    patchPluginXml {
        dependsOn("generatePluginXML")

        pluginId = prop("pluginId")
        version = prop("pluginVersion")
        sinceBuild = prop("pluginBuildSince")
    }

    signPlugin {
        certificateChain = System.getenv("CERTIFICATE_CHAIN")
        privateKey = System.getenv("PRIVATE_KEY")
        password = System.getenv("PRIVATE_KEY_PASSWORD")
    }

    publishPlugin {
        token = System.getenv("PUBLISH_TOKEN")
    }

    prepareSandbox {
        disabledPlugins.addAll(
            providers.fileContents(layout.projectDirectory.file("sandbox/disabled_plugins.txt"))
                .asText.map { it.lineSequence().filter(String::isNotBlank).toList() }
        )
    }
}

intellijPlatform {
    buildSearchableOptions = false
    instrumentCode = true
    projectName = project.name

    pluginVerification {
        ides {
            current()
        }
    }
}
