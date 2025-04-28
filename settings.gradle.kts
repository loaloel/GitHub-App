pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()

        maven("https://plugins.gradle.org/m2/")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        maven("https://plugins.gradle.org/m2/")
    }
}

rootProject.name = "GitHub App"
include(":app")
include(":core:networks")
include(":core:models")
include(":features:home")
include(":core:design-system:icons")
include(":core:extensions")
include(":features:webview")
