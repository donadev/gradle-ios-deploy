package com.donadev.kmm.ios_deploy.plugin.tasks

import com.donadev.kmm.ios_deploy.plugin.DeployExtension
import com.donadev.kmm.ios_deploy.plugin.models.PodRepository
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Exec
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File
import javax.inject.Inject

abstract class PodspecDeployTask: Exec() {


    init {
        description = "Deploys podspec"
        dependsOn("podspec")
        dependsOn("podRepo")
        workingDir = project.rootDir
    }

    @get:Input
    abstract val extension: Property<DeployExtension>

    override fun exec() {
        executable = "pod"
        args(mutableListOf<String>().apply {
            add("repo")
            add("push")
            add("--allow-warnings")
            add(extension.get().podRepository.get().name)
            add("${project.name}.podspec")
        })
        super.exec()
    }
}
