object PluginCoordinates {
    const val ID = "com.donadev.kmm.ios_deploy.plugin"
    const val GROUP = "com.donadev.kmm.ios_deploy"
    const val VERSION = "0.0.1"
    const val IMPLEMENTATION_CLASS = "com.donadev.kmm.ios_deploy.plugin.DeployPlugin"
}

object PluginBundle {
    const val VCS = "https://github.com/donadev/gradle-ios-deploy"
    const val WEBSITE = "https://github.com/donadev/gradle-ios-deploy"
    const val DESCRIPTION = "A simple Gradle Plugin for Kotlin Multi Platform that lets you upload your iOS artifact to Cocoapods or Swift Package Manager repositories."
    const val DISPLAY_NAME = "KMM iOS Deploy"
    val TAGS = listOf(
        "plugin",
        "gradle",
        "sample",
        "template"
    )
}

