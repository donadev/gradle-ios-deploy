package com.donadev.kmm.ios_deploy.plugin

import org.gradle.api.Project
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import javax.inject.Inject

@Suppress("UnnecessaryAbstractClass")
abstract class DeployExtension @Inject constructor() {

    abstract val summary: Property<String>
    abstract val url: Property<String>
    abstract val authors: Property<String>
    abstract val licenseType: Property<String>
    abstract val licenseFile: Property<String>

}
