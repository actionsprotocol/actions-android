plugins {
    `kotlin-dsl`
}

group = "app.actionsfun.build-conventions"

dependencies {
    implementation(libs.pluginKotlinGradle)
    implementation(projects.environment)
}
