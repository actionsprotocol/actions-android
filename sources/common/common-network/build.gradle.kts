plugins {
    id("convention.android-library")
    kotlin("plugin.serialization") version libs.versions.kotlin
}

android.namespace = "app.actionsfun.common.network"

dependencies {
    api(libs.bundles.retrofit)
    api(libs.kotlinCoroutinesCore)
    api(libs.kotlinSerialization)
    api(libs.okhttp)
    api(libs.okhttpInterceptor)
    implementation(libs.koinAndroid)
    implementation(projects.sources.common.commonUtil)
}