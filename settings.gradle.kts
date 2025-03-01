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
    repositories {
        google()
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")
        mavenCentral()
    }
}

rootProject.name = "Procrastination Panic"
include(":app")
