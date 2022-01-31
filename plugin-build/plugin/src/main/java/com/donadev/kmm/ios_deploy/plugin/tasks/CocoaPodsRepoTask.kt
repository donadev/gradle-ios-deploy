package com.donadev.kmm.ios_deploy.plugin.tasks

import com.donadev.kmm.ios_deploy.plugin.DeployExtension
import com.donadev.kmm.ios_deploy.plugin.models.PodRepository
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Exec
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

abstract class CocoaPodsRepoTask : Exec() {

    init {
        description = "Adds specified repo to cocoapods installation"
        this.isIgnoreExitValue = true
    }
    @get:Input
    abstract val extension: Property<DeployExtension>

    override fun exec() {
        executable = "pod"
        args(mutableListOf<String>().apply {
            add("repo")
            add("add")
            add(extension.get().podRepository.get().name)
            add(extension.get().podRepository.get().url)
        })
        super.exec()
    }
}