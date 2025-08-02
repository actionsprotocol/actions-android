package app.actionsfun.common.network.backendurl

import app.actionsfun.common.network.BuildConfig.BUILD_TYPE

sealed interface BackendUrl {

    val app: String
    val pinataGateway: String
    val pinataBaseUrl: String

    data object Dev : BackendUrl {
        override val app = "https://actions-backend-azil.onrender.com/"
        override val pinataGateway = "https://gateway.pinata.cloud"
        override val pinataBaseUrl = "https://sapphire-top-harrier-590.mypinata.cloud"
    }

    data object Prod : BackendUrl {
        override val app = "https://actions-backend-mainnet.onrender.com/"
        override val pinataGateway = "https://gateway.pinata.cloud"
        override val pinataBaseUrl = "https://sapphire-top-harrier-590.mypinata.cloud"
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
