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
    ":sources:app",

    ":sources:common:common-arch",
    ":sources:common:common-navigation",
    ":sources:common:common-network",
    ":sources:common:common-util",
    ":sources:common:common-ui",

    ":sources:repository:repository-actions",
    ":sources:repository:repository-onboarding",
    ":sources:repository:repository-preferences",
    ":sources:repository:repository-solana",
    ":sources:repository:repository-user",

    ":sources:feature:feature-splash",
    ":sources:feature:feature-onboarding",
    ":sources:feature:feature-home",
    ":sources:feature:feature-market",
)
