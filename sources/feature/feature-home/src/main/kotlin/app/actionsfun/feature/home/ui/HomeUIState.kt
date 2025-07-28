package app.actionsfun.feature.home.ui

import app.actionsfun.repository.actions.interactor.model.Market

internal sealed interface HomeUIState {

    data object Loading : HomeUIState

    data class Error(
        val message: String,
        val connectWallet: String,
        val retryButton: String,
        val publicKey: String?,
    ) : HomeUIState

    data class Success(
        val markets: List<Market>,
        val publicKey: String?,
        val connectWallet: String,
    ) : HomeUIState

    companion object {

        val Default = Loading
    }
}
