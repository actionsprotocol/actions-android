plugins {
    id("convention.android-library")
}

android.namespace = "app.actionsfun.common.arch"

dependencies {
    implementation(libs.bundles.androidxLifecycle)
    implementation(libs.timber)
}
