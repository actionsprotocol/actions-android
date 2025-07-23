plugins {
    id("com.android.application")
    id("convention.android-common")
    id("convention.kotlin-base")
    id("kotlin-android")
    id("kotlin-parcelize")
}

android {
    bundle {
        language {
            @Suppress("UnstableApiUsage")
            enableSplit = false
        }
    }

    defaultConfig {
        applicationId = "app.actionsfun"
    }

    lint {
        abortOnError = false
        warningsAsErrors = false
        textReport = true
        quiet = true
        checkReleaseBuilds = false
    }

    packaging {
        resources {
            excludes.add("LICENSE")
        }
    }
}
