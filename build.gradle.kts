fun prop(key: String) = project.findProperty(key).toString()

plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.9.0"
    id("org.jetbrains.kotlin.jvm") version "1.7.20"
}

dependencies {
    implementation("io.sentry:sentry:6.5.0")
    implementation(fileTree("hammer-tools/build/libs").also { it.include("*.jar") })
}

group = prop("pluginId")
version = prop("pluginVersion")

repositories {
    mavenCentral()
}

apply {
    plugin(net.rentalhost.plugins.gradle.ProjectTools::class)
}

intellij {
    pluginName.set(prop("pluginName"))
    version.set(prop("platformVersion"))
    type.set("IU")

    plugins.set(listOf("com.jetbrains.php:${prop("platformPhpBuild")}"))
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks {
    build {
        dependsOn("generatePluginXML")
        dependsOn("generateChangelog")
        dependsOn("generateDocumentation")
    }

    test {
        delete(file("$buildDir/classes"))

        isScanForTestClasses = false

        include("**/*TestCase.class")

        systemProperty("idea.split.test.logs", "true")
    }

    inspectClassesForKotlinIC {
        dependsOn("instrumentTestCode")
    }

    jar {
        dependsOn("instrumentTestCode")
    }

    wrapper {
        gradleVersion = prop("gradleVersion")
    }

    patchPluginXml {
        dependsOn("generatePluginXML")

        pluginId.set(prop("pluginId"))
        version.set(prop("pluginVersion"))
        sinceBuild.set(prop("pluginBuildSince"))
        untilBuild.set(prop("pluginBuildUntil"))
    }

    setupDependencies {
        dependsOn(gradle.includedBuild("hammer-tools").task(":jar"))

        doLast {
            // Fixes IDEA-298989.
            fileTree("$buildDir/instrumented/instrumentCode") { include("**/*Form.class") }.files.forEach { delete(it) }
        }
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }

    prepareSandbox {
        doLast {
            copy {
                from(file("$projectDir/sandbox/disabled_plugins.txt"))
                into(file("$buildDir/idea-sandbox/config"))
            }
        }
    }
}
