package io.github.donadev.kmm.ios_deploy.plugin.tasks

import org.gradle.api.tasks.Exec
import java.io.File
import javax.inject.Inject

abstract class BuildReleaseXCFrameworkTask @Inject constructor(xcFrameworkPath : String) : Exec() {

    init {
        description = "Generates XC Release Framework"
        dependsOn("linkReleaseFrameworkIosArm64")
        dependsOn("linkReleaseFrameworkIosX64")
        val xcFrameworkDest = File("${project.rootDir}/${xcFrameworkPath}")
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
