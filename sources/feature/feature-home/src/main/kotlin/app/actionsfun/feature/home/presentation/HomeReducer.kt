package app.actionsfun.feature.home.presentation

import app.actionsfun.common.arch.tea.dsl.DslReducer
import timber.log.Timber
import app.actionsfun.feature.home.presentation.model.HomeCommand as Command
import app.actionsfun.feature.home.presentation.model.HomeEffect as Effect
import app.actionsfun.feature.home.presentation.model.HomeEvent as Event
import app.actionsfun.feature.home.presentation.model.HomeState as State
import app.actionsfun.feature.home.presentation.model.HomeUIEvent as UIEvent

internal class HomeReducer : DslReducer<Command, Effect, Event, State>() {

    override fun reduce(event: Event) {
        when (event) {
            is UIEvent -> reduceUI(event)
            else -> reduceEvent(event)
        }
    }

    private fun reduceUI(event: UIEvent) {
        when (event) {
            is UIEvent.ConnectWalletClick -> commands(Command.ConnectWallet)
            is UIEvent.ProfileClick -> effects(Effect.OpenProfile)
            is UIEvent.RetryLoadingClick -> {
                state { copy(markets = null, error = null) }
                commands(Command.LoadMarkets)
            }
        }
    }

    private fun reduceEvent(event: Event) {
        when (event) {
            is Event.Initialize -> {
                commands(
                    Command.LoadMarkets,
                    Command.ObserveWallet,
                )
            }

            is Event.MarketsLoaded -> {
                state { copy(markets = event.markets) }
            }

            is Event.MarketsLoadingError -> {
                state { copy(error = event.error) }
            }

            is Event.ConnectedWalletChanged -> {
                state { copy(publicKey = event.publicKey) }
            }

            is Event.ConnectWalletFailed -> {
                effects(Effect.ShowErrorToast("Something went wrong"))
            }

            else -> Unit
        }
    }
}
