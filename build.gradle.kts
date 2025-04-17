import org.jetbrains.intellij.platform.gradle.IntelliJPlatformType
import org.jetbrains.intellij.platform.gradle.TestFrameworkType
import org.jetbrains.intellij.platform.gradle.models.ProductRelease
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun prop(key: String) = project.findProperty(key).toString()

plugins {
    id("java")
    id("org.jetbrains.intellij.platform") version "2.5.0"
    id("org.jetbrains.kotlin.jvm") version "2.1.10"
}

dependencies {
    implementation("io.sentry:sentry:7.22.5")
    //implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.25")

    intellijPlatform {
        phpstorm(prop("platformVersion"), false)

        bundledPlugin("com.jetbrains.php")

        pluginVerifier()
        zipSigner()
        //instrumentationTools()

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
        //dependsOn("compileTestKotlin")
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

intellijPlatform {
    buildSearchableOptions = false
    instrumentCode = true
    projectName = project.name

    pluginVerification {
        ides {
            ide(IntelliJPlatformType.PhpStorm, "2025.1")
            //local(file("C:\\Users\\Ronny\\AppData\\Local\\Programs\\PhpStorm"))
            recommended()
            select {
                types = listOf(IntelliJPlatformType.PhpStorm)
                channels = listOf(ProductRelease.Channel.RELEASE)
                sinceBuild = "251"
                untilBuild = "251.*"
            }
        }
    }
}

val compileKotlin: KotlinCompile by tasks

compileKotlin.compilerOptions {
    jvmTarget.set(JvmTarget.JVM_21)
}

val compileTestKotlin: KotlinCompile by tasks

compileTestKotlin.compilerOptions {
    jvmTarget.set(JvmTarget.JVM_21)
}
