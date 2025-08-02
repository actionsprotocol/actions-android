package app.actionsfun.feature.profile.ui

import app.actionsfun.repository.actions.internal.api.model.MarketUiState
import java.time.OffsetDateTime

internal data class ProfileUIState(
    val socials: SocialLinks,
    val wallet: WalletState,
    val howItWorks: HowItWorks,
    val marketsSectionTitle: String,
    val marketsState: MarketsState,
) {

    companion object {
        val Preview = ProfileUIState(
            socials = SocialLinks(
                github = "https://github.com/actionsprotocol/actions-android",
                twitter = "https://x.com/actionsdotfun"
            ),
            wallet = WalletState.Connected(
                publicKey = "0xdb...C32d"
//                connectButton = "Connect Wallet",
            ),
            howItWorks = HowItWorks(
                label = "Essentials",
                title = "How it works",
                text = "Welcome to actions.fun! We’re so glad you’re here. We’ve created this guid eto help with the basics of actions.fun and get you started on your new Web3 journey.",
                url = ""
            ),
            marketsSectionTitle = "Your Markets",
            marketsState = MarketsState.Loading,
        )
    }
}

internal sealed interface WalletState {
    val publicKey: String

    data class NotConnected(
        val connectButton: String,
    ) : WalletState {
        override val publicKey: String = ""
    }

    data class Connected(
        override val publicKey: String,
    ) : WalletState
}

internal data class SocialLinks(
    val github: String,
    val twitter: String,
)

internal data class HowItWorks(
    val label: String,
    val title: String,
    val text: String,
    val url: String,
)

internal sealed interface MarketsState {

    data object Loading : MarketsState

    data class Error(
        val title: String,
        val retryButton: String,
    ) : MarketsState

    data class Empty(
        val title: String,
        val description: String,
    ) : MarketsState

    data class Content(
        val markets: List<MarketUI>,
    ) : MarketsState
}

internal data class MarketUI(
    val address: String,
    val title: String,
    val chance: Int,
    val volume: Float,
    val voteOption: Boolean,
    val endsAt: OffsetDateTime,
    val status: MarketUiState,
    val claimed: Boolean,
    val canClaim: Boolean,
    val claimAmount: Float,
)
