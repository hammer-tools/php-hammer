fun properties(key: String) = project.findProperty(key).toString()

plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.6.0"
    id("org.jetbrains.kotlin.jvm") version "1.7.0"
}

group = properties("pluginGroup")
version = properties("pluginVersion")

repositories {
    mavenCentral()
}

intellij {
    pluginName.set(properties("pluginName"))
    version.set(properties("platformVersion"))
    type.set(properties("platformType"))

    plugins.set(properties("platformPlugins").split(','))
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = properties("javaVersion")
        targetCompatibility = properties("javaVersion")
    }

    test {
        jvmArgs("--add-exports", "java.base/jdk.internal.vm=ALL-UNNAMED")

        isScanForTestClasses = false

        include("**/*TestCase.class")
        exclude("net/rentalhost/plugins/services/TestCase.class")
    }

    inspectClassesForKotlinIC {
        dependsOn("instrumentTestCode")
    }

    jar {
        dependsOn("instrumentTestCode")
    }

    wrapper {
        gradleVersion = properties("gradleVersion")
    }

    patchPluginXml {
        version.set(properties("pluginVersion"))
        sinceBuild.set(properties("pluginSinceBuild"))
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
        jvmArgs("-Xmx2048m")

        jbrVersion.set("11_0_15b2043.56")

        ideDir.set(file("${System.getProperty("user.home")}\\AppData\\Local\\JetBrains\\Toolbox\\apps\\PhpStorm\\ch-0\\${properties("platformPhpBuild")}"))
    }
}
