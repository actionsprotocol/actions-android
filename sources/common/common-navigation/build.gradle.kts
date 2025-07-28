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
    implementation(libs.androidxCoreKtx)
    implementation(libs.androidxActivityKtx)
    implementation(libs.androidxActivityCompose)
    implementation(libs.androidxComposeMaterial3)
    implementation(libs.androidxAppcompat)
    implementation(libs.kotlinReflect)
    implementation(libs.kotlinSerialization)
}