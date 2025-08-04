package app.actionsfun.feature.home.presentation.model

import app.actionsfun.repository.actions.interactor.model.Market

internal data class HomeState(
    val markets: List<Market>?,
    val publicKey: String?,
    val error: Throwable?,
    val page: Int,
) {

    companion object {

        val Default = HomeState(
            markets = null,
            publicKey = null,
            error = null,
            page = 0,
        )
    }
}
