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
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "jonbeo-win"
include(":app")

// core
include(
    ":core:domain",
    ":core:data",
    ":core:local",
    ":core:remote",
    ":core:common",
)

// feature
include(
    ":feature:encourage",
    ":feature:jonbeocount",
    ":feature:asset",
)
