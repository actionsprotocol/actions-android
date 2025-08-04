package app.actionsfun.feature.market.ui

import app.actionsfun.common.ui.market.MarketStatusUI
import app.actionsfun.feature.market.ui.components.market.CommentUI
import app.actionsfun.feature.market.ui.components.market.MarketUI
import java.time.OffsetDateTime


internal data class MarketUIState(
    val video: VideoUI?,
    val market: MarketUI?,
    val deposit: DepositUI,
    val replies: RepliesUI,
    val selectedCard: Int,
) {
    val pages: Int
        get() = if (video == null) 3 else 4

    val marketInfoIndex: Int
        get() = if (video == null) 0 else 1

    val videoIndex: Int
        get() = if (video == null) -1 else 0

    val depositIndex: Int
        get() = if (video == null) 1 else 2

    val repliesIndex: Int
        get() = if (video == null) 2 else 3
}

internal data class VideoUI(
    val title: String,
    val creatorUsername: String,
    val creatorAvatar: String,
    val createdAt: OffsetDateTime,
    val marketStatusUI: MarketStatusUI,
    val videoUrl: String?,
    val button: String,
)

internal data class DepositUI(
    val selectedOption: Boolean,
    val amount: Float,
    val title: String,
    val label: String,
    val balance: Float,
    val quickAmounts: List<QuickAmountUI>,
    val yesPercentage: String,
    val noPercentage: String,
    val button: String,
    val buttonEnabled: Boolean,
    val enabled: Boolean,
    val loading: Boolean,
    val infoMessage: String,
    val howItWorks: HowItWorks = HowItWorks(
        label = "Essentials",
        title = "How it works",
        text = "Welcome to actions.fun! We're so glad you're here. We've created this guide to help with the basics of actions.fun and get you started on your new Web3 journey.",
        url = "https://app.actions.fun/mobile-faq"
    ),
) {

    companion object {
        val Empty = DepositUI(
            selectedOption = false,
            amount = 0f,
            title = "",
            label = "",
            balance = 0f,
            quickAmounts = emptyList(),
            yesPercentage = "",
            noPercentage = "",
            button = "",
            enabled = false,
            loading = false,
            buttonEnabled = false,
            infoMessage = "",
        )
    }
}

internal data class RepliesUI(
    val replies: List<CommentUI>,
    val button: String,
    val isWalletConnected: Boolean = false,
)

internal data class QuickAmountUI(
    val value: Float,
    val label: String,
    val enabled: Boolean = true,
)

internal data class HowItWorks(
    val label: String,
    val title: String,
    val text: String,
    val url: String,
)
