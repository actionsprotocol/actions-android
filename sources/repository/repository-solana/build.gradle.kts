plugins {
    id("convention.android-library")
    kotlin("plugin.serialization") version libs.versions.kotlin
}

android.namespace = "app.actionsfun.repository.solana"

dependencies {
    api(libs.solanaWeb3)
    implementation(projects.sources.repository.repositoryPreferences)
    implementation(projects.sources.repository.repositoryUser)
    implementation(projects.sources.common.commonNetwork)
    implementation(projects.sources.common.commonUtil)
    implementation(libs.solanaMobile)
    implementation(libs.solanaRpcCore)
    implementation(libs.solanaRpcSolana)
    implementation(libs.timber)
    implementation(libs.koinAndroid)
    implementation(libs.kotlinSerialization)
    implementation(libs.kotlinCoroutinesCore)
    api("com.ditchoom:buffer:1.4.2")
}
