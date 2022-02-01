package io.github.donadev.kmm.ios_deploy.plugin.tasks

import org.gradle.api.tasks.Exec
import java.io.File
import javax.inject.Inject

abstract class BuildReleaseXCFrameworkTask @Inject constructor(xcFrameworkPath : String) : Exec() {

    private val buildConfiguration = "release"
    private val rootDir = project.projectDir
    private val libName = project.name


    private val x64FrameworkPath = "$rootDir/build/bin/iosX64/${buildConfiguration}Framework/$libName.framework"
    private val x64DebugSymbolsPath = "$rootDir/build/bin/iosX64/${buildConfiguration}Framework/$libName.framework.dSYM"
    private val arm64FrameworkPath = "$rootDir/build/bin/iosArm64/${buildConfiguration}Framework/$libName.framework"
    private val arm64DebugSymbolsPath = "$rootDir/build/bin/iosArm64/${buildConfiguration}Framework/$libName.framework.dSYM"
    private val simulatorArm64FrameworkPath = "$rootDir/build/bin/iosSimulatorArm64/${buildConfiguration}Framework/$libName.framework"
    private val simulatorArm64DebugSymbolsPath = "$rootDir/build/bin/iosSimulatorArm64/${buildConfiguration}Framework/$libName.framework.dSYM"

    private val xcFrameworkDest = File("${project.rootDir}/${xcFrameworkPath}")

    init {
        description = "Generates XC Release Framework"
        dependsOn("link${buildConfiguration.capitalize()}FrameworkIosArm64")
        dependsOn("link${buildConfiguration.capitalize()}FrameworkIosX64")
    }

    override fun exec() {
        xcFrameworkDest.deleteRecursively()
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
        super.exec()
    }
}
