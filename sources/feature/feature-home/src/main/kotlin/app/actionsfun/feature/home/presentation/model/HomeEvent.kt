package app.actionsfun.feature.home.presentation.model

import app.actionsfun.repository.actions.interactor.model.Market

internal sealed interface HomeEvent {

    data object Initialize : HomeEvent

    data class MarketsLoadingError(
        val error: Throwable,
    ) : HomeEvent

    data class MarketsLoaded(
        val markets: List<Market>,
    ) : HomeEvent

    data class ConnectedWalletChanged(
        val userId: String,
        val publicKey: String?,
        val authToken: String,
    ) : HomeEvent

    data object EmptyEvent : HomeEvent

    data object ConnectWalletFailed : HomeEvent
}

internal sealed interface HomeUIEvent : HomeEvent {

    data object ConnectWalletClick : HomeUIEvent

    data object ProfileClick : HomeUIEvent

    data object RetryLoadingClick : HomeUIEvent

    data class PageChanged(val index: Int) : HomeUIEvent
}