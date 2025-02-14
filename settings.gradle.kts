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

rootProject.name = "AvitoPlayer"
include(":app")
include(":common")
include(":resources")
include(":ui_kit")
include(":feature_downloaded_tracks")
include(":feature_api_tracks")
include(":data")
include(":domain")
include(":feature_audio_player")
