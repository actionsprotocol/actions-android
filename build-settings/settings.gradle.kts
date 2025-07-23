pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
        maven { setUrl("https://api.mapbox.com/downloads/v2/releases/maven") }
    }
}

include(":dependencies")