import org.jetbrains.changelog.closure

fun properties(key: String) = project.findProperty(key).toString()

plugins {
  java

  id("com.diffplug.spotless") version "5.12.4"
  id("org.jetbrains.changelog") version "1.1.2"
  id("org.jetbrains.intellij") version "1.3.0"
}

repositories {
  mavenCentral()
}

project.group = properties("pluginGroup")
project.version = properties("pluginVersion")

intellij {
  pluginName = "Hacker News"
  version = properties("platformVersion")
  type = properties("platformType")
  downloadSources = properties("platformDownloadSources").toBoolean()
  updateSinceUntilBuild = true
}

changelog {
  version = properties("pluginVersion")
  groups = emptyList()
}

tasks {
  withType<JavaCompile> {
    sourceCompatibility = "11"
    targetCompatibility = "11"
  }

  patchPluginXml {
    version(properties("pluginVersion"))
    sinceBuild(properties("pluginSinceBuild"))
    untilBuild(properties("pluginUntilBuild"))

    changeNotes(
      closure {
        changelog.getLatest().toHTML()
      }
    )
  }

  runPluginVerifier {
    ideVersions(properties("pluginVerifierIdeVersions"))
  }

  publishPlugin {
    dependsOn("patchChangelog")
    token(System.getenv("JETBRAINS_PUBLISH_TOKEN"))
    channels(properties("pluginVersion").split('-').getOrElse(1) { "default" }.split('.').first())
  }
}

spotless {
  kotlinGradle {
    ktlint().userData(mapOf(
      "indent_size" to "2",
      "continuation_indent_size" to "2"
    ))
    endWithNewline()
  }

  java {
    googleJavaFormat()
  }
}

tasks.withType<Wrapper> {
  gradleVersion = "6.8.3"
  distributionType = Wrapper.DistributionType.ALL
}
