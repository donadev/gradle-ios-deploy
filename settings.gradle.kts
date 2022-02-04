pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = ("gradle-ios-deploy")

include(":example")
includeBuild("plugin-build")
