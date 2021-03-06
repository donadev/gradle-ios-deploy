package io.github.donadev.kmm.ios_deploy.plugin

import io.github.donadev.kmm.ios_deploy.plugin.models.SpecRepository
import org.gradle.api.provider.Property
import javax.inject.Inject

@Suppress("UnnecessaryAbstractClass")
abstract class DeployExtension @Inject constructor() {

    abstract val summary: Property<String>
    abstract val specRepository: Property<SpecRepository>
    abstract val gitUrl: Property<String>
    abstract val homepage: Property<String>
    abstract val authors: Property<String>
    abstract val licenseType: Property<String>
    abstract val licenseFile: Property<String>

}
