package app.actionsfun.feature.market.presentation

import androidx.compose.ui.graphics.Color
import app.actionsfun.common.arch.tea.component.UiStateMapper
import app.actionsfun.feature.market.ui.DepositUI
import app.actionsfun.feature.market.ui.RepliesUI
import app.actionsfun.feature.market.ui.components.market.MarketUI
import app.actionsfun.repository.actions.internal.api.model.MarketUiState
import app.actionsfun.repository.actions.internal.api.model.UIMarketState
import java.time.OffsetDateTime
import java.util.UUID

import app.actionsfun.feature.market.presentation.model.MarketState as State
import app.actionsfun.feature.market.ui.MarketUIState as UIState

internal class MarketUIStateMapper : UiStateMapper<State, UIState> {

    override fun map(state: State): UIState {
        return UIState(
            video = null,
            market = MarketUI(
                address = UUID.randomUUID().toString(),
                createdAt = OffsetDateTime.now().minusMinutes(24),
                endsAt = OffsetDateTime.now().plusHours(2).plusMinutes(21),
                volumeYes = 5.6,
                volumeNo = 4.4,
                volume = 10.0,
                image = "",
                title = "Will pump.fun have higher trading volume than bonk.fun in exactly 24 hours?",
                description = "This market will resolve to YES if, exactly 24 hours after creation, pump.fun shows higher total trading volume than bonk.fun. Verification will be based on publicly available sources such as Axiom dashboards or on-chain data explorers.",
                creatorUsername = "@narracanz",
                creatorAvatar = "",
                replies = listOf(),
                marketUIState = MarketUiState(
                    state = UIMarketState.Active,
                ),
                accentColor = Color(0xFFEC58A9),
                button = "Trade",
            ),
            deposit = DepositUI(
                selectedOption = false,
                amount = 0f,
                title = "",
                balance = 0f,
                error = null,
                success = null,
                quickAmounts = emptyList(),
                yesPercentage = "",
                noPercentage = "",
                button = ""
            ),
            replies = RepliesUI(
                replies = emptyList(),
                button = ""
            ),
            selectedCard = 0,
        )
    }
}
