plugins {
    alias(libs.plugins.composeCompiler)
    kotlin("plugin.serialization") version "2.1.21"
    id("convention.android-application")
}

android.namespace = "app.actions"

repositories {
    google()
    mavenCentral()
    maven { setUrl("https://jitpack.io") }
    maven { setUrl("https://plugins.gradle.org/m2/") }
    maven { setUrl("https://api.mapbox.com/downloads/v2/releases/maven") }
}

dependencies {
    implementation(projects.sources.common.commonUi)
    implementation(projects.sources.common.commonUtil)
    implementation(projects.sources.common.commonNetwork)
    implementation(projects.sources.common.commonNavigation)

    implementation(projects.sources.repository.repositoryActions)
    implementation(projects.sources.repository.repositoryUser)
    implementation(projects.sources.repository.repositorySolana)
    implementation(projects.sources.repository.repositoryPreferences)
    implementation(projects.sources.repository.repositoryOnboarding)

    implementation(projects.sources.feature.featureSplash)
    implementation(projects.sources.feature.featureOnboarding)

    implementation(libs.androidxAppcompat)
    implementation(libs.androidxActivityKtx)
    implementation(libs.androidXWorker)
    implementation(libs.androidxCoreKtx)
    implementation(libs.androidBrowserHelper)
    implementation(libs.kotlinSerialization)
    implementation(libs.material)
    implementation(libs.koinAndroid)
    implementation(libs.koinCompose)
    implementation(libs.timber)
}
