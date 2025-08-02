package app.actionsfun.feature.market.presentation

import app.actionsfun.common.arch.tea.dsl.DslReducer
import app.actionsfun.repository.actions.internal.api.model.isActive
import app.actionsfun.feature.market.presentation.model.MarketCommand as Command
import app.actionsfun.feature.market.presentation.model.MarketEffect as Effect
import app.actionsfun.feature.market.presentation.model.MarketEvent as Event
import app.actionsfun.feature.market.presentation.model.MarketState as State
import app.actionsfun.feature.market.presentation.model.MarketUIEvent as UIEvent

internal class MarketReducer(
    private val marketAddress: String,
) : DslReducer<Command, Effect, Event, State>() {

    override fun reduce(event: Event) {
        when (event) {
            is UIEvent -> reduceUI(event)
            else -> reduceEvent(event)
        }
    }

    private fun reduceUI(event: UIEvent) {
        when (event) {
            is UIEvent.DepositQuickAmountClicked -> {
                state {
                    copy(
                        deposit = state.deposit.copy(
                            amount = state.deposit.amount + event.amount,
                        )
                    )
                }
            }

            is UIEvent.DepositAmountChanged -> {
                state {
                    copy(
                        deposit = state.deposit.copy(
                            amount = event.value,
                        )
                    )
                }
            }

            is UIEvent.DepositOptionChanged -> {
                state {
                    copy(
                        deposit = state.deposit.copy(
                            option = event.option,
                        )
                    )
                }
            }

            is UIEvent.DepositActionButtonClicked -> {
                when {
                    state.walletPublicKey.isNullOrEmpty() -> {
                        commands(Command.ConnectWallet)
                    }
                    state.deposit.enabled -> {
                        state {
                            copy(
                                deposit = state.deposit.copy(
                                    enabled = false,
                                    loading = true,
                                )
                            )
                        }
                        commands(
                            Command.DepositSol(
                                marketAddress = marketAddress,
                                amountSol = state.deposit.amount,
                                option = state.deposit.option,
                            )
                        )
                    }
                    state.market?.claimStatus?.canClaim == true -> {
                        commands(
                            Command.ClaimSol(
                                marketAddress = marketAddress,
                            )
                        )
                    }
                }
            }

            is UIEvent.SendReply -> {
                commands(
                    Command.SendReply(
                        marketAddress = marketAddress,
                        text = event.text,
                    )
                )
            }
            
            is UIEvent.ConnectWallet -> {
                commands(Command.ConnectWallet)
            }

            else -> Unit
        }
    }

    private fun reduceEvent(event: Event) {
        when (event) {
            is Event.LoadMarket -> {
                state { copy(isLoading = true, error = null) }
                commands(Command.LoadMarket(event.marketAddress))
            }

            is Event.MarketLoaded -> {
                state {
                    copy(
                        market = event.market,
                        deposit = state.deposit.copy(
                            enabled = event.market.claimStatus == null
                                    && event.market.market.uiState.isActive,
                            loading = false,
                        ),
                        isLoading = false,
                        error = null
                    )
                }
            }

            is Event.MarketLoadingError -> {
                state { copy(isLoading = false, error = event.error) }
            }

            is Event.ObserveNetwork -> {
                commands(Command.ObserveNetwork)
            }

            is Event.ConnectionStateChanged -> {
                if (event.connected) {
                    commands(Command.LoadMarket(marketAddress))
                }
            }

            is Event.BalanceLoaded -> {
                state { copy(balanceSol = event.balance.toFloat()) }
            }

            is Event.SolDepositFailed -> {
                state {
                    copy(
                        deposit = deposit.copy(
                            loading = false,
                            enabled = true,
                        )
                    )
                }
                effects(Effect.ShowErrorToast(message = "Deposit failed"))
            }

            is Event.SolDeposited -> {
                state {
                    copy(
                        deposit = deposit.copy(
                            loading = false,
                            enabled = false,
                        )
                    )
                }
                commands(Command.LoadMarket(marketAddress))
                effects(Effect.ShowSuccessToast(message = "Deposited successfully"))
            }

            is Event.SolClaimed -> {
                state {
                    copy(
                        deposit = deposit.copy(
                            loading = false,
                            enabled = false,
                        )
                    )
                }
                commands(Command.LoadMarket(marketAddress))
                effects(Effect.ShowSuccessToast(message = "Claimed successfully"))
            }

            is Event.SolClaimFailed -> {
                state {
                    copy(
                        deposit = deposit.copy(
                            loading = false,
                            enabled = true,
                        )
                    )
                }
                effects(Effect.ShowErrorToast(message = "Failed to claim SOL"))
            }
            
            is Event.ReplySent -> {
                commands(Command.LoadMarket(marketAddress))
            }
            
            is Event.ReplySendFailed -> {
                effects(Effect.ShowErrorToast(message = "Failed to send reply"))
            }
            
            is Event.WalletStateChanged -> {
                state {
                    copy(walletPublicKey = event.publicKey)
                }
            }
            
            is Event.WalletConnectionFailed -> {
                effects(Effect.ShowErrorToast(message = "Failed to connect wallet"))
            }

            else -> Unit
        }
    }
} 