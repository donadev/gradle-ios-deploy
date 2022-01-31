package com.donadev.kmm.ios_deploy.plugin

import com.donadev.kmm.ios_deploy.plugin.tasks.BuildReleaseXCFrameworkTask
import com.donadev.kmm.ios_deploy.plugin.tasks.PodspecTask
import com.donadev.kmm.ios_deploy.plugin.tasks.SwiftPackageTask
import org.gradle.api.Plugin
import org.gradle.api.Project


abstract class DeployPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val extensionName = "iosDeploy"
        val groupName = "ios-deploy"
        val extension = project.extensions.create(extensionName, DeployExtension::class.java)
        val xcFrameworkPath = "${project.name}/build/xc/${project.name}.xcframework"

        project.tasks.register("buildReleaseXCFramework", BuildReleaseXCFrameworkTask::class.java, xcFrameworkPath)
        project.tasks.register("podspec", PodspecTask::class.java) {
            it.extension.set(extension)
            it.xcFrameworkPath.set(xcFrameworkPath)
            it.group = groupName
        }
        project.tasks.register("swiftPackage", SwiftPackageTask::class.java) {
            it.extension.set(extension)
            it.xcFrameworkPath.set(xcFrameworkPath)
            it.group = groupName
        }
        //TODO
        /*project.task("deployPodspecToRepo") {
            this.group = groupName
            dependsOn("podspec")
            doLast { deployPodspec(project, extension) }
        }*/
        //TODO
        /*project.task("deployPackageToCollection") {
            this.group = groupName
            dependsOn("swiftPackage")
            doLast { deploySwiftPackage(project, extension) }
        }*/

    }
}
