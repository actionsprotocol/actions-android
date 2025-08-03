package app.actionsfun.feature.profile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.actionsfun.common.ui.components.button.ThreeDimensionalButton
import app.actionsfun.common.ui.components.threed.Gyroscopic3DLayout
import app.actionsfun.common.ui.components.threed.ThreeDimensionalLayout
import app.actionsfun.common.ui.style.AppTheme
import app.actionsfun.common.ui.style.Body14Medium
import app.actionsfun.common.ui.style.Body14Regular
import app.actionsfun.common.ui.style.Body16Medium
import app.actionsfun.common.util.Timer
import app.actionsfun.common.util.timeLeftString
import app.actionsfun.feature.profile.ui.MarketUI
import app.actionsfun.repository.actions.internal.api.model.MarketUiState
import app.actionsfun.repository.actions.internal.api.model.UIMarketState
import app.actionsfun.repository.actions.internal.api.model.isActive
import app.actionsfun.repository.actions.internal.api.model.isCancelled
import app.actionsfun.repository.actions.internal.api.model.isDeciding
import app.actionsfun.repository.actions.internal.api.model.isFinished
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.OffsetDateTime
import java.util.UUID
import kotlin.time.Duration.Companion.seconds

@Composable
internal fun Market(
    state: MarketUI,
    modifier: Modifier = Modifier,
    claimClick: (String) -> Unit = { Unit },
) {
    ThreeDimensionalLayout(
        modifier = modifier
            .fillMaxWidth(),
        color = Color(0xFFFFFFFF),
        shadowColor = Color(0xFFEBEBEF),
        shadowAlignment = Alignment.BottomStart,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Header(state = state)

            Chips(state = state)

            if (state.claimAmount > 0f) {
                if (!state.claimed && state.canClaim) {
                    ThreeDimensionalButton(
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                shape = RoundedCornerShape(24.dp),
                                color = Color(0xFF21D979)
                            )
                            .fillMaxWidth()
                            .height(36.dp),
                        text = "Claim ${state.claimAmount} SOL",
                        textColor = Color(0xFF21D979),
                        color = Color(0xFFFFFFFF),
                        shadowColor = Color(0xFF21D979),
                        shadowAlignment = Alignment.BottomCenter,
                        onClick = { claimClick(state.address) }
                    )
                }
            }
        }
    }
}

@Composable
private fun Header(
    state: MarketUI,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.Top,
    ) {
        Text(
            modifier = Modifier
                .weight(1f),
            text = state.title,
            style = Body16Medium,
            color = AppTheme.Colors.Text.Primary,
        )

        MarketStatus(
            state = state,
        )
    }
}

@Composable
private fun MarketStatus(
    state: MarketUI,
    modifier: Modifier = Modifier,
) {
    var endsAt by remember {
        mutableStateOf(state.endsAt.timeLeftString())
    }

    LaunchedEffect(Unit) {
        Timer.endless(rate = 1.seconds).start()
            .onEach { endsAt = state.endsAt.timeLeftString() }
            .launchIn(this)
    }

    when {
        state.status.isActive -> {
            Box(
                modifier = modifier
                    .border(
                        color = Color(0xFFE9E9ED),
                        width = 1.dp,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(vertical = 6.dp, horizontal = 10.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = endsAt,
                    style = Body16Medium,
                    color = AppTheme.Colors.Text.Primary,
                    modifier = Modifier,
                )
            }
        }
        state.status.isCancelled -> {
            Box(
                modifier = modifier
                    .border(
                        color = Color(0xFFE9E9ED),
                        width = 1.dp,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(vertical = 6.dp, horizontal = 10.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Cancelled",
                    style = Body16Medium,
                    color = AppTheme.Colors.Text.Primary,
                    modifier = Modifier,
                )
            }
        }
        state.status.isDeciding -> {
            Box(
                modifier = modifier
                    .border(
                        color = Color(0xFFE9E9ED),
                        width = 1.dp,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(vertical = 6.dp, horizontal = 10.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Deciding",
                    style = Body16Medium,
                    color = AppTheme.Colors.Text.Primary,
                    modifier = Modifier,
                )
            }
        }
        state.status.isFinished -> {
            when (state.status.state) {
                UIMarketState.FinalizedNo -> {
                    ThreeDimensionalButton(
                        modifier = Modifier
                            .border(
                                width = 0.5.dp,
                                color = Color(0xFFF55B7F),
                                shape = RoundedCornerShape(24.dp),
                            )
                            .width(76.dp)
                            .height(36.dp),
                        text = "NO",
                        color = Color(0xFFFFFFFF),
                        shadowColor = Color(0xFFF55B7F),
                        textColor = Color(0xFFF55B7F),
                        clickable = false,
                    )
                }
                UIMarketState.FinalizedYes -> {
                    ThreeDimensionalButton(
                        modifier = Modifier
                            .border(
                                width = 0.5.dp,
                                color = Color(0xFF21D979),
                                shape = RoundedCornerShape(24.dp),
                            )
                            .width(76.dp)
                            .height(36.dp),
                        text = "YES",
                        textColor = Color(0xFF21D979),
                        color = Color(0xFFFFFFFF),
                        shadowColor = Color(0xFF21D979),
                        clickable = false,
                        shadowAlignment = Alignment.BottomCenter,
                    )
                }
                else -> Unit
            }
        }
    }
}

@Composable
private fun Chips(
    state: MarketUI,
    modifier: Modifier = Modifier,
) {
    FlowRow(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            modifier = Modifier
                .border(width = 1.dp, color = Color(0xFFF4F4F6), shape = RoundedCornerShape(24.dp))
                .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "VOTE",
                style = Body14Regular,
                color = AppTheme.Colors.Text.Secondary,
            )

            Text(
                text = if (state.voteOption) "YES" else "NO",
                style = Body14Medium,
                color = if (state.voteOption) Color(0xFF21D979) else Color(0xFFF55B7F),
            )
        }

        Row(
            modifier = Modifier
                .border(width = 1.dp, color = Color(0xFFF4F4F6), shape = RoundedCornerShape(24.dp))
                .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "CHANCE",
                style = Body14Regular,
                color = AppTheme.Colors.Text.Secondary,
            )

            Text(
                text = "${state.chance}%",
                style = Body14Medium,
                color = AppTheme.Colors.Text.Primary,
            )
        }

        Row(
            modifier = Modifier
                .border(width = 1.dp, color = Color(0xFFF4F4F6), shape = RoundedCornerShape(24.dp))
                .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "VOL.",
                style = Body14Regular,
                color = AppTheme.Colors.Text.Secondary,
            )

            Text(
                text = "${state.volume}",
                style = Body14Medium,
                color = AppTheme.Colors.Text.Primary,
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Column(
        modifier = Modifier
            .background(AppTheme.Colors.Background.Primary)
            .fillMaxSize()
            .padding(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Market(
            modifier = Modifier
                .fillMaxWidth(),
            state = MarketUI(
                address = UUID.randomUUID().toString(),
                title = "Will pump.fun have higher trading volume than bonk.fun in exactly 24 hours?",
                chance = 32,
                volume = 21.3f,
                voteOption = false,
                endsAt = OffsetDateTime.now().plusHours(2).plusMinutes(21),
                status = MarketUiState(
                    state = UIMarketState.FinalizedNo,
                    winningOption = null,
                ),
                claimed = false,
                canClaim = false,
                claimAmount = 2.1f,
            )
        )
    }
}