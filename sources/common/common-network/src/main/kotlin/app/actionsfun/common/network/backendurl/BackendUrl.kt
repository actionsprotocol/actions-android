package app.actionsfun.common.network.backendurl

import app.actionsfun.common.network.BuildConfig.BUILD_TYPE

sealed interface BackendUrl {

    val app: String

    data object Dev : BackendUrl {
        override val app = "https://actions-backend-azil.onrender.com/"
    }

    data object Prod : BackendUrl {
        override val app = "https://actions-backend-mainnet.onrender.com/"
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
