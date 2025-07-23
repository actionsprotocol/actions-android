plugins {
    id("convention.android-library")
    kotlin("plugin.serialization") version libs.versions.kotlin
}

android.namespace = "app.actionsfun.repository.user"

dependencies {
    implementation(projects.sources.repository.repositoryPreferences)
    implementation(projects.sources.common.commonNetwork)
    implementation(projects.sources.common.commonUtil)
    implementation(libs.timber)
    implementation(libs.koinAndroid)
    implementation(libs.kotlinSerialization)
    implementation(libs.kotlinCoroutinesCore)
}
