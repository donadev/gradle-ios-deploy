package io.github.donadev.kmm.ios_deploy.plugin.tasks

import io.github.donadev.kmm.ios_deploy.plugin.DeployExtension
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class SwiftPackageTask : DefaultTask() {

    init {
        description = "Generates swiftPackage"
        dependsOn("buildReleaseXCFramework")
    }

    @get:Input
    abstract val extension: Property<DeployExtension>

    @get:Input
    abstract val xcFrameworkPath: Property<String>

    private fun getSwiftPackage(project: Project, extension: DeployExtension) : String {
        return """""".trimIndent()
    }

    @TaskAction
    fun execute() {
        val podspec = getSwiftPackage(project, extension = extension.get())
        val outFile = File(project.rootDir, "Package.swift")
        outFile.writeText(podspec)
    }
}
