package app.actionsfun.common.network.backendurl

import app.actionsfun.common.network.BuildConfig.BUILD_TYPE

sealed interface BackendUrl {

    val app: String

    data object Dev : BackendUrl {
        override val app = ""
    }

    data object Prod : BackendUrl {
        override val app = ""
    }

    @Suppress("KotlinConstantConditions")
    companion object {

        val Environmental: BackendUrl
            get() = when (BUILD_TYPE) {
                "release" -> Prod
                else -> Dev
            }
    }
}
