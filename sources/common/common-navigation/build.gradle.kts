plugins {
    alias(libs.plugins.composeCompiler)
    id("convention.android-library-compose")
    kotlin("plugin.serialization") version libs.versions.kotlin
}

android.namespace = "app.actionsfun.common.navigation"

dependencies {
    api(libs.bundles.androidxNavigation)
    api(libs.androidxNavigationCompose)
    implementation(platform(libs.androidxComposeBom))
    implementation(libs.androidxComposeFoundation)
    implementation(libs.kotlinReflect)
    implementation(libs.kotlinSerialization)
}