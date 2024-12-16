import org.jetbrains.intellij.platform.gradle.TestFrameworkType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun prop(key: String) = project.findProperty(key).toString()

plugins {
    id("java")
    id("org.jetbrains.intellij.platform") version "2.0.1"
    id("org.jetbrains.kotlin.jvm") version "1.9.25"
}

dependencies {
    implementation("io.sentry:sentry:7.12.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.25")

    intellijPlatform {
        phpstorm(prop("platformVersion"), false)

        bundledPlugin("com.jetbrains.php")

        pluginVerifier()
        zipSigner()
        instrumentationTools()

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
    jvmToolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks {
    build {
        dependsOn("generatePluginXML")
        dependsOn("generateChangelog")
        dependsOn("generateDocumentation")
    }

    buildSearchableOptions {
        enabled = false
    }

    test {
        delete(file("build/classes"))

        isScanForTestClasses = false

        include("**/*TestCase.class")

        filter {
            excludeTestsMatching("net.rentalhost.plugins.php.hammer.TestCase")
        }

        systemProperty("idea.split.test.logs", "true")
    }

    jar {
        dependsOn("instrumentTestCode")
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

    setupDependencies {
        doLast {
            // Fixes IDEA-298989.
            fileTree("build/instrumented/instrumentCode") { include("**/*Form.class") }.files.forEach { delete(it) }
        }
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
        doLast {
            copy {
                from(file("sandbox/disabled_plugins.txt"))
                into(file("build/idea-sandbox/config"))
            }
        }
    }
}

val compileKotlin: KotlinCompile by tasks

compileKotlin.kotlinOptions {
    jvmTarget = "21"
}

val compileTestKotlin: KotlinCompile by tasks

compileTestKotlin.kotlinOptions {
    jvmTarget = "21"
}
