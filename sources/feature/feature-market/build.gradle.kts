plugins {
    alias(libs.plugins.composeCompiler)
    id("convention.android-library-compose")
}

android.namespace = "app.actionsfun.feature.market"

dependencies {
    implementation(projects.sources.common.commonUi)
    implementation(projects.sources.common.commonUtil)
    implementation(projects.sources.common.commonNetwork)
    implementation(projects.sources.common.commonArch)
    implementation(projects.sources.common.commonNavigation)
    implementation(projects.sources.repository.repositoryActions)
    implementation(projects.sources.repository.repositoryUser)
    implementation(projects.sources.repository.repositoryOnboarding)
    implementation(libs.androidxAppcompat)
    implementation(libs.timber)
    implementation(libs.koinAndroid)
    implementation(libs.coilCore)
    implementation(libs.coilCompose)
    implementation(libs.pageIndicator)
}
