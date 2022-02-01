import java.util.*
import java.io.*

plugins {
    kotlin("jvm")
    id("java-gradle-plugin")
    id("com.gradle.plugin-publish")
    id("maven-publish")
}

dependencies {
    implementation(kotlin("stdlib-jdk7"))
    implementation(gradleApi())

    testImplementation(TestingLib.JUNIT)
}
fun fileCredentials() = Properties().apply {
    load(FileInputStream(rootProject.file("github.properties")))
}

fun ciCredentials() = Properties().apply {
    this["gpr.usr"] = System.getenv("GITHUB_USERNAME").toString()
    this["gpr.key"] = System.getenv("GITHUB_TOKEN").toString()
}

val isCI: Boolean = System.getenv("GITHUB_ACTIONS").toBoolean()
val githubProperties = when {
    isCI -> ciCredentials()
    else -> fileCredentials()
}


java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

gradlePlugin {
    plugins {
        create(PluginCoordinates.ID) {
            id = PluginCoordinates.ID
            implementationClass = PluginCoordinates.IMPLEMENTATION_CLASS
            version = PluginCoordinates.VERSION
        }
    }
}

publishing {
    repositories {
        maven {
            name = "Remote"
            url = uri("https://maven.pkg.github.com/donadev/gradle-ios-deploy")
            credentials {
                username = githubProperties["gpr.usr"].toString()
                password = githubProperties["gpr.key"].toString()
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
            groupId = PluginCoordinates.GROUP
            artifactId = PluginCoordinates.ID
            version = PluginCoordinates.VERSION
        }
    }
}

// Configuration Block for the Plugin Marker artifact on Plugin Central
pluginBundle {
    website = PluginBundle.WEBSITE
    vcsUrl = PluginBundle.VCS
    description = PluginBundle.DESCRIPTION
    tags = PluginBundle.TAGS

    plugins {
        getByName(PluginCoordinates.ID) {
            displayName = PluginBundle.DISPLAY_NAME
        }
    }

    mavenCoordinates {
        groupId = PluginCoordinates.GROUP
        artifactId = PluginCoordinates.ID
        version = PluginCoordinates.VERSION
    }
}

tasks.create("setupPluginUploadFromEnvironment") {
    doLast {
        val key = System.getenv("GRADLE_PUBLISH_KEY")
        val secret = System.getenv("GRADLE_PUBLISH_SECRET")

        if (key == null || secret == null) {
            throw GradleException("gradlePublishKey and/or gradlePublishSecret are not defined environment variables")
        }

        System.setProperty("gradle.publish.key", key)
        System.setProperty("gradle.publish.secret", secret)
    }
}
