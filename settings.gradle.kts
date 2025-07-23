rootProject.name = "actions"

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
        maven { setUrl("https://plugins.gradle.org/m2/") }
        maven { setUrl("https://api.mapbox.com/downloads/v2/releases/maven") }
    }
    pluginManagement {
        repositories {
            google()
            mavenCentral()
            maven { setUrl("https://plugins.gradle.org/m2/") }
        }
    }
}

includeBuild("build-settings")
includeBuild("build-conventions")

include(
    ":sources:common:common-arch",
    ":sources:common:common-network",
    ":sources:common:common-util",
)
