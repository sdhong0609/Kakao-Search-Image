pluginManagement {
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

rootProject.name = "Kakao Search Image"
include(":app")
include(":core:data")
include(":core:local")
include(":core:remote")
include(":core:ui")
include(":feature")
include(":feature:favorite")
include(":feature:search")
include(":feature:image_detail")
include(":feature:common")
