package app.actionsfun.feature.market.presentation

import androidx.compose.ui.graphics.Color
import app.actionsfun.common.arch.tea.component.UiStateMapper
import app.actionsfun.common.ui.avatar.avatarByWalletAddress
import app.actionsfun.feature.market.ui.DepositUI
import app.actionsfun.feature.market.ui.QuickAmountUI
import app.actionsfun.feature.market.ui.RepliesUI
import app.actionsfun.feature.market.ui.components.market.CommentUI
import app.actionsfun.feature.market.ui.components.market.MarketUI
import app.actionsfun.repository.actions.internal.api.model.isActive

import app.actionsfun.feature.market.presentation.model.MarketState as State
import app.actionsfun.feature.market.ui.MarketUIState as UIState

internal class MarketUIStateMapper : UiStateMapper<State, UIState> {

    override fun map(state: State): UIState {
        val market = state.market

        return UIState(
            video = null,
            market = market?.let {
                MarketUI(
                    address = market.market.address,
                    createdAt = market.market.createdAt,
                    endsAt = market.market.endsAt,
                    volumeYes = market.market.yesVolume,
                    volumeNo = market.market.noVolume,
                    volume = market.market.volume,
                    image = market.market.image,
                    title = market.market.title,
                    description = market.market.description,
                    creatorUsername = market.market.creatorTwitterUsername,
                    creatorAvatar = market.market.creatorTwitterImage,
                    replies = market.market.replies.map { reply ->
                        CommentUI(
                            image = reply.username.avatarByWalletAddress,
                            author = reply.username,
                            text = reply.content,
                            createdAt = reply.timestamp,
                        )
                    },
                    marketUIState = market.market.uiState,
                    accentColor = Color(0xFFEC58A9),
                    button = "Trade",
                )
            },
            deposit = state.buildDepositState(),
            replies = RepliesUI(
                replies = market?.market?.replies?.map { reply ->
                    CommentUI(
                        image = reply.username.avatarByWalletAddress,
                        author = reply.username,
                        text = reply.content,
                        createdAt = reply.timestamp,
                    )
                } ?: emptyList(),
                button = "",
                isWalletConnected = !state.walletPublicKey.isNullOrEmpty(),
            ),
            selectedCard = 0,
        )
    }

    private fun State.buildDepositState(): DepositUI {
        val market = this.market?.market ?: return DepositUI.Empty
        val claim = this.market.claimStatus

        return DepositUI(
            selectedOption = claim?.userOption ?: deposit.option,
            amount = claim?.claimableAmountSol ?: deposit.amount,
            title = market.title,
            enabled = market.uiState.isActive,
            loading = this.deposit.loading,
            label = when {
                claim != null -> {
                    when {
                        claim.winningOption == claim.userOption -> "You won"
                        claim.winningOption != claim.userOption -> "You lost"
                        claim.claimableAmountSol > 0f -> "Your deposit"
                        else -> ""
                    }
                }
                else -> "Enter amount"
            },
            balance = balanceSol,
            quickAmounts = listOf(
                QuickAmountUI(
                    value = 0.1f,
                    label = "+0.1",
                    enabled = balanceSol >= 0.1f
                ),
                QuickAmountUI(
                    value = 0.5f,
                    label = "+0.5",
                    enabled = balanceSol >= 0.5f
                ),
                QuickAmountUI(
                    value = 1f,
                    label = "+1",
                    enabled = balanceSol >= 1f
                ),
                QuickAmountUI(
                    value = balanceSol - 0.01f,
                    label = "Max",
                    enabled = true,
                ),
            ),
            yesPercentage = "${market.yesPercentage}%",
            noPercentage = "${market.noPercentage}%",
            button = when {
                walletPublicKey.isNullOrEmpty() -> "Connect wallet"
                market.uiState.isActive -> "Deposit"
                claim?.canClaim == true -> "Claim"
                else -> "Market is closed"
            },
            buttonEnabled = (market.uiState.isActive && deposit.amount > 0f)
                    || claim?.canClaim == true,
            infoMessage = when {
                market.uiState.isActive && deposit.amount > balanceSol -> "Insufficient balance"
                claim != null && claim.userBetSol > 0 -> "Your bet: ${claim.userBetSol} SOL"
                else -> ""
            }
        )
    }
}
