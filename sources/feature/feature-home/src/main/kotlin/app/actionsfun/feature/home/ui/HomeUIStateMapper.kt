package app.actionsfun.feature.home.ui

import app.actionsfun.common.arch.tea.component.UiStateMapper
import app.actionsfun.feature.home.presentation.model.HomeState
import app.actionsfun.feature.home.ui.HomeUIState

import app.actionsfun.feature.home.presentation.model.HomeState as State
import app.actionsfun.feature.home.ui.HomeUIState as UIState

internal class HomeUIStateMapper : UiStateMapper<State, UIState> {

    override fun map(state: HomeState): HomeUIState {
        return when {
            state.error != null -> {
                HomeUIState.Error(
                    message = "Something went wrong \uD83E\uDEE0",
                    connectWallet = "Connect wallet",
                    retryButton = "Retry",
                    publicKey = state.publicKey,
                )
            }
            state.markets.isNullOrEmpty() -> {
                HomeUIState.Loading
            }
            else -> {
                HomeUIState.Success(
                    markets = state.markets,
                    publicKey = state.publicKey,
                    connectWallet = "Connect wallet",
                    pageIndex = state.page,
                )
            }
        }
    }
}
