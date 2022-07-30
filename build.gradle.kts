import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun prop(key: String) = project.findProperty(key).toString()

plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.7.0"
    id("org.jetbrains.kotlin.jvm") version "1.7.0"
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

tasks {
    java {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_11.toString()
        }
    }

    test {
        delete(file("$buildDir/classes"))

        jvmArgs("--add-exports", "java.base/jdk.internal.vm=ALL-UNNAMED")

        isScanForTestClasses = false

        include("**/*TestCase.class")
        exclude("net/rentalhost/plugins/services/TestCase.class")

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

    runIde {
        jvmArgs("--add-exports", "java.base/jdk.internal.vm=ALL-UNNAMED")
        jvmArgs("-Xmx4096m")

        jbrVersion.set("11_0_15b2043.56")

        ideDir.set(file("${System.getProperty("user.home")}\\AppData\\Local\\JetBrains\\Toolbox\\apps\\PhpStorm\\${prop("platformPhpToolbox")}"))
    }
}
