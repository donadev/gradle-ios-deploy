# gradle-ios-deploy 🐘

![Language](https://img.shields.io/github/languages/top/donadev/gradle-ios-deploy?color=blue&logo=kotlin)

A simple Gradle Plugin for Kotlin Multi Platform that lets you upload your iOS artifact to Cocoapods or Swift Package Manager private Spec Repos.

## Usage

- Firstly, import the plugin in your module-level build.gradle
  ```kotlin
  plugins {
    kotlin("multiplatform")
    id("io.github.donadev.kmm.ios_deploy.plugin")
  }
  ```
- Configure it by adding:
  ```kotlin
  iosDeploy {
    licenseFile.set("LICENSE") //Path for the repository LICENSE file
    licenseType.set("MIT") //License type
    authors.set("John Doe, Mark Black") //Authors comma-separated list
    summary.set("...") //summary for the repo
    homepage.set("...") //Webpage url 
    gitUrl.set("...") //url for the git repository
    podRepository.set(
      PodRepository(
        "name", //alias of the spec repository in your local system
        "url" //url of the private spec repository
      )
    )
  }
  ```
- And that's it! After syncing, you will have the following tasks available:
  - `buildReleaseXCFramework`: Creates a XCFramework from the KMM ios artifacts
  - `podspec` Creates a podspec on $ROOT/$module_name.podspec
  - `podspecDeploy` Deploys the podspec to the private Spec Repo using pod cli.
