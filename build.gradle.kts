import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun prop(key: String) = project.findProperty(key).toString()

plugins {
  id("java")
  id("org.jetbrains.intellij") version "1.15.0"
  id("org.jetbrains.kotlin.jvm") version "1.9.20-Beta2"
}

dependencies {
  implementation("io.sentry:sentry:6.30.0")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.20-Beta2")
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
  type.set("PS")

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

  buildSearchableOptions {
    enabled = false
  }

  test {
    delete(file("build/classes"))

    isScanForTestClasses = false

    include("**/*TestCase.class")

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

    pluginId.set(prop("pluginId"))
    version.set(prop("pluginVersion"))
    sinceBuild.set(prop("pluginBuildSince"))
    untilBuild.set(prop("pluginBuildUntil"))
  }

  setupDependencies {
    doLast {
      // Fixes IDEA-298989.
      fileTree("build/instrumented/instrumentCode") { include("**/*Form.class") }.files.forEach { delete(it) }
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
        from(file("sandbox/disabled_plugins.txt"))
        into(file("build/idea-sandbox/config"))
      }
    }
  }
}

val compileKotlin: KotlinCompile by tasks

compileKotlin.kotlinOptions {
  jvmTarget = "17"
}

val compileTestKotlin: KotlinCompile by tasks

compileTestKotlin.kotlinOptions {
  jvmTarget = "17"
}
