package io.github.donadev.kmm.ios_deploy.plugin.tasks

import io.github.donadev.kmm.ios_deploy.plugin.DeployExtension
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
            add(extension.get().specRepository.get().name)
            add(extension.get().specRepository.get().url)
        })
        super.exec()
    }
}