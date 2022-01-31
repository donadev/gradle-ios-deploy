package com.donadev.kmm.ios_deploy.plugin.tasks

import com.donadev.kmm.ios_deploy.plugin.DeployExtension
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class PodspecTask : DefaultTask() {

    init {
        description = "Generates podspec"
        dependsOn("buildReleaseXCFramework")
    }

    @get:Input
    abstract val extension: Property<DeployExtension>

    @get:Input
    abstract val xcFrameworkPath: Property<String>

    private fun getPodspec(project: Project, extension: DeployExtension) : String {
        return """
            Pod::Spec.new do |spec|
                spec.name                     = '${project.name}'
                spec.version                  = '${project.version}'
                spec.homepage                 = '${extension.url.get()}'
                spec.source                   = { :git => "${extension.url.get()}", :tag => spec.version.to_s }
                spec.authors                  = '${extension.authors.get()}'
                spec.license                  = { :type => '${extension.licenseType.get()}', :file => '${extension.licenseFile.get()}' }
                spec.summary                  = '${extension.summary.get()}'
                spec.vendored_frameworks      = "$xcFrameworkPath"
                spec.libraries                = "c++"
                spec.static_framework         = true
                spec.module_name              = "#{spec.name}_umbrella"
                spec.ios.deployment_target = '11.0'
            end
        """.trimIndent()
    }

    @TaskAction
    fun execute() {
        val podspec = getPodspec(project, extension = extension.get())
        val outFile = File(project.rootDir, "${project.name}.podspec")
        outFile.writeText(podspec)
    }
}
