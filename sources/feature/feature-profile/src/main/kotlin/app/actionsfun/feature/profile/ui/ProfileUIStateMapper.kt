package app.actionsfun.feature.profile.ui

import app.actionsfun.common.arch.tea.component.UiStateMapper
import app.actionsfun.feature.profile.ui.ProfileUIState
import app.actionsfun.feature.profile.presentation.model.ProfileState as State
import app.actionsfun.feature.profile.ui.ProfileUIState as UIState

internal class ProfileUIStateMapper : UiStateMapper<State, UIState> {

    override fun map(state: State): UIState {
        val marketsState = when {
            state.isLoading -> MarketsState.Loading
            state.error != null -> MarketsState.Error(
                title = "Failed to load markets",
                retryButton = "Retry"
            )
            state.marketsWithClaimStatuses.isEmpty() -> MarketsState.Empty(
                title = "No markets yet",
                description = "You haven't participated in any markets"
            )
            else -> MarketsState.Content(
                markets = state.marketsWithClaimStatuses.map { market ->
                    MarketUI(
                        address = market.market.address,
                        title = market.market.title,
                        chance = market.market.yesPercentage,
                        volume = market.market.volume,
                        voteOption = market.claimStatus?.userOption ?: true,
                        endsAt = market.market.endsAt,
                        status = market.market.uiState,
                        claimed = market.claimStatus?.alreadyClaimed ?: false,
                        claimAmount = market.claimStatus?.claimableAmountSol?.toFloat() ?: 0f,
                        canClaim = market.claimStatus?.canClaim ?: false,
                    )
                }
            )
        }
        
        return ProfileUIState(
            socials = SocialLinks(
                github = "https://github.com/actionsprotocol/actions-android",
                twitter = "https://x.com/actionsdotfun"
            ),
            wallet = when {
                state.publicKey.isNullOrEmpty() -> {
                    WalletState.NotConnected("Connect wallet")
                }
                else -> WalletState.Connected(formatPublicKey(state.publicKey))
            },
            howItWorks = HowItWorks(
                label = "Essentials",
                title = "How it works",
                text = "Welcome to actions.fun! We’re so glad you’re here. We’ve created this guide to help with the basics of actions.fun and get you started on your new Web3 journey.",
                url = "https://app.actions.fun/mobile-faq"
            ),
            marketsSectionTitle = "Markets",
            marketsState = marketsState,
            showWalletOptionsSheet = state.showWalletOptionsSheet
        )
    }
    
    private fun formatPublicKey(publicKey: String): String {
        return if (publicKey.length > 8 && publicKey != "Not connected") {
            "${publicKey.take(4)}...${publicKey.takeLast(4)}"
        } else {
            publicKey
        }
    }
} 