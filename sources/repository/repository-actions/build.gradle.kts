plugins {
    id("convention.android-library")
    kotlin("plugin.serialization") version libs.versions.kotlin
}

android.namespace = "app.actionsfun.repository.actions"

dependencies {
    implementation(projects.sources.repository.repositoryPreferences)
    implementation(projects.sources.repository.repositoryUser)
    implementation(projects.sources.repository.repositorySolana)
    implementation(projects.sources.repository.repositoryPinata)
    implementation(projects.sources.common.commonNetwork)
    implementation(projects.sources.common.commonUtil)
    implementation(libs.timber)
    implementation(libs.koinAndroid)
    implementation(libs.kotlinSerialization)
    implementation(libs.kotlinCoroutinesCore)
}
