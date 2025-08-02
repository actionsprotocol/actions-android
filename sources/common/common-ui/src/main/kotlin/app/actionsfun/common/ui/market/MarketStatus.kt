package app.actionsfun.common.ui.market

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import app.actionsfun.common.ui.style.AppTheme
import app.actionsfun.common.ui.style.Body16Medium
import app.actionsfun.common.util.Timer
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.Duration
import java.time.OffsetDateTime
import kotlin.time.Duration.Companion.seconds

sealed interface MarketStatusUI {
    data class Active(
        val endsAt: OffsetDateTime
    ) : MarketStatusUI

    data class Finished(
        val option: Boolean
    ) : MarketStatusUI

    data object Deciding : MarketStatusUI

    data object Cancelled : MarketStatusUI
}

@Composable
fun MarketStatus(
    state: MarketStatusUI,
    modifier: Modifier = Modifier,
    textColor: Color = AppTheme.Colors.Text.Primary,
) {
    AnimatedContent(
        targetState = state,
        modifier = modifier
            .border(
                color = Color(0xFFE9E9ED),
                width = 1.dp,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(vertical = 6.dp, horizontal = 10.dp),
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        contentAlignment = Alignment.Center,
    ) { state ->
        when (state) {
            is MarketStatusUI.Active -> {
                TimerState(
                    endsAt = state.endsAt,
                    textColor = textColor,
                )
            }
            is MarketStatusUI.Deciding -> {
                TextContent(
                    text = "Deciding",
                    textColor = textColor,
                )
            }
            is MarketStatusUI.Cancelled -> {
                TextContent(
                    text = "Cancelled",
                    textColor = textColor,
                )
            }
            is MarketStatusUI.Finished -> {
                when (state.option) {
                    true -> {
                        TextContent(
                            text = "YES",
                            textColor = Color(0xFF21D979),
                        )
                    }
                    false -> {
                        TextContent(
                            text = "NO",
                            textColor = Color(0xFFF55B7F),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TimerState(
    endsAt: OffsetDateTime,
    textColor: Color,
    modifier: Modifier = Modifier,
) {
    var timeLeft by remember {
        mutableStateOf(endsAt.timeLeftString())
    }

    LaunchedEffect(Unit) {
        Timer.endless(rate = 1.seconds).start()
            .onEach { timeLeft = endsAt.timeLeftString() }
            .launchIn(this)
    }

    TextContent(
        modifier = modifier,
        text = timeLeft,
        textColor = textColor,
    )
}

@Composable
private fun TextContent(
    text: String,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = Body16Medium,
        color = textColor,
        modifier = modifier,
    )
}

@Preview
@Composable
private fun Preview() {
    Column(
        modifier = Modifier
            .background(AppTheme.Colors.Background.Primary)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MarketStatus(
            state = MarketStatusUI.Deciding,
        )

        MarketStatus(
            state = MarketStatusUI.Cancelled,
        )

        MarketStatus(
            state = MarketStatusUI.Finished(true),
        )

        MarketStatus(
            state = MarketStatusUI.Finished(false),
        )

        MarketStatus(
            state = MarketStatusUI.Active(
                endsAt = OffsetDateTime.now()
                    .plusDays(1)
            ),
        )
    }
}

private fun OffsetDateTime.timeLeftString(): String {
    val now = OffsetDateTime.now()
    val duration = Duration.between(now, this)

    return when {
        duration.isNegative -> "00:00:00"
        else -> {
            val hours = duration.toHours()
            val minutes = duration.toMinutesPart()
            val seconds = duration.toSecondsPart()
            "${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
        }
    }
}