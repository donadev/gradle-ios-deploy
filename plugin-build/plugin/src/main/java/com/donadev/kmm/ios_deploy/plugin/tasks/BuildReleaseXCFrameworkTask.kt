package com.donadev.kmm.ios_deploy.plugin.tasks

import com.donadev.kmm.ios_deploy.plugin.DeployExtension
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.*
import org.gradle.api.tasks.options.Option
import java.io.File

abstract class BuildReleaseXCFrameworkTask : Exec() {

    init {
        description = "Generates XC Release Framework"
        dependsOn("linkReleaseFrameworkIosArm64")
        dependsOn("linkReleaseFrameworkIosX64")
    }

    @get:Input
    abstract val xcFrameworkPath: Property<String>

    @TaskAction
    fun execute() {
        val xcFrameworkDest = File("${project.rootDir}/${xcFrameworkPath.get()}")
        xcFrameworkDest.deleteRecursively()
        val rootDir = project.projectDir
        val libName = project.name
        val arm64FrameworkPath = "$rootDir/build/bin/iosArm64/releaseFramework/$libName.framework"
        val arm64DebugSymbolsPath =
            "$rootDir/build/bin/iosArm64/releaseFramework/$libName.framework.dSYM"

        val x64FrameworkPath = "$rootDir/build/bin/iosX64/releaseFramework/$libName.framework"
        val x64DebugSymbolsPath = "$rootDir/build/bin/iosX64/releaseFramework/$libName.framework.dSYM"

        executable = "xcodebuild"
        args(mutableListOf<String>().apply {
            add("-create-xcframework")
            add("-output")
            add(xcFrameworkDest.path)

            // Real Device
            add("-framework")
            add(arm64FrameworkPath)
            add("-debug-symbols")
            add(arm64DebugSymbolsPath)

            // Simulator
            add("-framework")
            add(x64FrameworkPath)
            add("-debug-symbols")
            add(x64DebugSymbolsPath)
        })
    }
}
