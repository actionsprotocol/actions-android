plugins {
    alias(libs.plugins.composeCompiler)
    id("convention.android-library-compose")
}

android.namespace = "app.actionsfun.common.ui"

dependencies {
    api(libs.androidxActivityCompose)
    api(libs.androidxComposeAnimation)
    api(libs.androidxComposeMaterial)
    api(libs.androidxComposeMaterial3)
    api(libs.androidxComposeRuntime)
    api(libs.androidxComposeUi)
    api(libs.androidxComposeUiTooling)
    api(libs.androidxComposeUiToolingPreview)
    api(libs.composeShimmer)
    api(libs.androidxBrowser)
    api(libs.androidxComposeFoundation)
    api(platform(libs.androidxComposeBom))
    implementation(libs.androidxFragmentKtx)
    implementation(libs.coilCompose)
    implementation(libs.jsoup)
}
