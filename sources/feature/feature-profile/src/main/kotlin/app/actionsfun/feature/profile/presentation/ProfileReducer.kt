package app.actionsfun.feature.profile.presentation

import app.actionsfun.common.arch.tea.dsl.DslReducer
import app.actionsfun.feature.profile.presentation.model.ProfileCommand
import timber.log.Timber
import app.actionsfun.feature.profile.presentation.model.ProfileCommand as Command
import app.actionsfun.feature.profile.presentation.model.ProfileEffect as Effect
import app.actionsfun.feature.profile.presentation.model.ProfileEvent as Event
import app.actionsfun.feature.profile.presentation.model.ProfileState as State
import app.actionsfun.feature.profile.presentation.model.ProfileUIEvent as UIEvent

internal class ProfileReducer : DslReducer<Command, Effect, Event, State>() {

    override fun reduce(event: Event) {
        when (event) {
            is UIEvent -> reduceUI(event)
            else -> reduceEvent(event)
        }
    }

    private fun reduceUI(event: UIEvent) {
        when (event) {
            is UIEvent.PublicKeyClicked -> {

            }
            is UIEvent.ClaimClicked -> commands(Command.ClaimSol(event.marketAddress))
            is UIEvent.ConnectWalletClick -> commands(ProfileCommand.ConnectWallet)
            is UIEvent.RetryLoadMarketsClick -> {
                state { copy(isLoading = true, error = null) }
                commands(ProfileCommand.LoadMarkets)
            }
        }
    }

    private fun reduceEvent(event: Event) {
        when (event) {
            is Event.Initialize -> {
                state { copy(isLoading = true, error = null) }
                commands(
                    Command.ObserveMarkets,
                    Command.ObserveWallet,
                )
            }
            is Event.MarketsLoaded -> {
                state { 
                    copy(
                        marketsWithClaimStatuses = event.markets,
                        isLoading = false,
                        error = null
                    ) 
                }
            }
            is Event.MarketsLoadingError -> {
                state { copy(isLoading = false, error = event.error) }
            }

            is Event.ConnectedWalletChanged -> {
                state { copy(publicKey = event.publicKey) }
            }

            is Event.ConnectWalletFailed -> {
                effects(Effect.ShowErrorToast("Failed to connect wallet"))
            }

            is Event.SolClaimFailed -> {
                effects(Effect.ShowErrorToast("Failed to claim winnings"))
            }

            is Event.SolClaimed -> {
                effects(Effect.ShowSuccessToast("Claimed successfully"))
                commands(Command.LoadMarkets)
            }

            else -> Unit
        }
    }
} 