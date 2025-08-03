@file:OptIn(ExperimentalFoundationApi::class)

package app.actionsfun.feature.market.ui.components.market

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.actionsfun.common.ui.avatar.avatarByWalletAddress
import app.actionsfun.common.ui.components.button.PrimaryButton
import app.actionsfun.common.ui.components.threed.Gyroscopic3DLayout
import app.actionsfun.common.ui.modifier.bouncingClickable
import app.actionsfun.common.ui.style.AppTheme
import app.actionsfun.common.ui.style.Body12Regular
import app.actionsfun.common.ui.style.Body14Regular
import app.actionsfun.common.ui.style.Body16Medium
import app.actionsfun.common.ui.style.Heading4
import app.actionsfun.common.util.Timer
import app.actionsfun.common.util.timeLeftString
import app.actionsfun.common.util.timeRelativeString
import app.actionsfun.feature.market.ui.components.Section
import app.actionsfun.feature.market.ui.components.SectionedArcProgress
import app.actionsfun.repository.actions.internal.api.model.MarketUiState
import app.actionsfun.repository.actions.internal.api.model.UIMarketState
import app.actionsfun.repository.actions.internal.api.model.isActive
import app.actionsfun.repository.actions.internal.api.model.isCancelled
import app.actionsfun.repository.actions.internal.api.model.isDeciding
import app.actionsfun.repository.actions.internal.api.model.isFinished
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.OffsetDateTime
import java.util.UUID
import kotlin.time.Duration.Companion.seconds

@Stable
internal data class MarketUI(
    val address: String,
    val image: String,
    val title: String,
    val description: String,
    val creatorUsername: String,
    val creatorAvatar: String,
    val createdAt: OffsetDateTime,
    val endsAt: OffsetDateTime,
    val volume: Float,
    val volumeYes: Float,
    val volumeNo: Float,
    val replies: List<CommentUI>,
    val marketUIState: MarketUiState,
    val accentColor: Color,
    val button: String,
) {
    val chance: Int
        get() = when {
            volume == 0f -> 50
            else -> ((volumeYes / volume) * 100).toInt()
        }

    val chanceYes: Float
        get() = if (volumeYes == 0f && volumeNo == 0f) 50f else volumeYes

    val chanceNo: Float
        get() = if (volumeYes == 0f && volumeNo == 0f) 50f else volumeNo

    val creatorTwitter: String
        get() = "https://x.com/$creatorUsername"
}

@Stable
internal data class CommentUI(
    val image: Int,
    val author: String,
    val text: String,
    val createdAt: OffsetDateTime,
)

@Composable
internal fun Market(
    state: MarketUI,
    modifier: Modifier = Modifier,
    buttonClick: () -> Unit = { Unit },
    profileClick: (String) -> Unit = { Unit },
    viewAllRepliesClick: () -> Unit = { Unit },
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Gyroscopic3DLayout(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = AppTheme.Colors.Background.Surface,
            shape = RoundedCornerShape(32.dp),
            shadowColor = Color(0xFFEC58A9),
            shadowAlignment = Alignment.BottomStart,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        width = 2.dp,
                        color = Color(0xFFEC58A9),
                        shape = RoundedCornerShape(32.dp)
                    )
                    .padding(all = 24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                Header(
                    state = state,
                    profileClick = profileClick,
                )

                MarketDescription(state)

                AboutMarket(state)

                Replies(
                    state = state,
                    viewAllClick = viewAllRepliesClick,
                )
            }
        }

        PrimaryButton(
            text = state.button,
            modifier = Modifier
                .fillMaxWidth(),
            onClick = buttonClick,
        )
    }
}

@Composable
private fun Header(
    state: MarketUI,
    modifier: Modifier = Modifier,
    profileClick: (String) -> Unit = { Unit },
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .bouncingClickable { profileClick(state.creatorTwitter) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(state.creatorAvatar)
                .crossfade(true)
                .build(),
            placeholder = painterResource(app.actionsfun.common.ui.R.drawable.ic_photo_placeholder),
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentDescription = null,
        )

        Column(Modifier.weight(1f)) {
            Text(
                text = state.creatorUsername,
                style = Body16Medium,
                color = AppTheme.Colors.Text.Primary,
            )
            Text(
                text = state.createdAt.timeRelativeString(),
                style = Body12Regular,
                color = AppTheme.Colors.Text.Secondary,
            )
        }

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
        when {
            state.marketUIState.isActive -> {
                Text(
                    text = endsAt,
                    style = Body16Medium,
                    color = AppTheme.Colors.Text.Primary,
                    modifier = Modifier,
                )
            }

            state.marketUIState.isFinished -> {
                Text(
                    text = "Finished",
                    style = Body16Medium,
                    color = AppTheme.Colors.Text.Primary,
                    modifier = Modifier,
                )
            }

            state.marketUIState.isDeciding -> {
                Text(
                    text = "Deciding",
                    style = Body16Medium,
                    color = AppTheme.Colors.Text.Primary,
                    modifier = Modifier,
                )
            }

            state.marketUIState.isCancelled -> {
                Text(
                    text = "Cancelled",
                    style = Body16Medium,
                    color = AppTheme.Colors.Text.Primary,
                    modifier = Modifier,
                )
            }
        }
    }
}

@Composable
private fun MarketDescription(
    state: MarketUI,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = state.title,
            style = Heading4,
            color = AppTheme.Colors.Text.Primary,
        )
        Text(
            text = state.description,
            style = Body14Regular,
            color = AppTheme.Colors.Text.Secondary,
        )
    }
}

@Composable
private fun AboutMarket(
    state: MarketUI,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = "About market",
            style = Body16Medium,
            color = AppTheme.Colors.Text.Primary,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(state.image)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(app.actionsfun.common.ui.R.drawable.ic_photo_placeholder),
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentDescription = null,
            )

            VerticalDivider(
                modifier = Modifier.size(16.dp),
                thickness = 1.dp,
                color = Color(0xFFE9E9ED),
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Column {
                    Text(
                        text = "Chance",
                        style = Body12Regular,
                        color = AppTheme.Colors.Text.Secondary,
                    )
                    Row {
                        Text(
                            text = state.chance.toString(),
                            style = Heading4,
                            color = AppTheme.Colors.Text.Primary
                        )
                        Text(
                            text = "%",
                            style = Heading4,
                            color = AppTheme.Colors.Text.Primary,
                        )
                    }
                }

                SectionedArcProgress(
                    modifier = Modifier
                        .width(48.dp),
                    strokeWidth = 6.dp,
                    sections = listOf(
                        Section(
                            color = Color(0xFF21D979),
                            value = state.chanceYes
                        ),
                        Section(
                            color = Color(0xFFE9E9ED),
                            value = state.chanceNo,
                        ),
                    )
                )
            }

            VerticalDivider(
                modifier = Modifier.size(16.dp),
                thickness = 1.dp,
                color = Color(0xFFE9E9ED),
            )

            Column {
                Text(
                    text = "Volume",
                    style = Body12Regular,
                    color = AppTheme.Colors.Text.Secondary,
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(app.actionsfun.common.ui.R.drawable.ic_solana),
                        contentDescription = null,
                    )
                    Text(
                        text = "%.2f".format(state.volume),
                        style = Heading4,
                        color = AppTheme.Colors.Text.Primary
                    )
                }
            }
        }
    }
}

@Composable
private fun Replies(
    state: MarketUI,
    modifier: Modifier = Modifier,
    viewAllClick: () -> Unit = { Unit },
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "Replies",
                style = Body16Medium,
                color = AppTheme.Colors.Text.Primary,
            )
            Text(
                modifier = Modifier
                    .bouncingClickable { viewAllClick() },
                text = "View All (${state.replies.size})",
                style = Body14Regular,
                color = AppTheme.Colors.Text.Primary,
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            state.replies.forEach { reply ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    AsyncImage(
                        model = reply.author.avatarByWalletAddress,
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape),
                        contentDescription = null,
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = reply.text,
                        style = Body14Regular,
                        color = AppTheme.Colors.Text.Primary,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Market(
        modifier = Modifier
            .fillMaxSize(),
        state = MarketUI(
            address = UUID.randomUUID().toString(),
            createdAt = OffsetDateTime.now().minusMinutes(24),
            endsAt = OffsetDateTime.now().plusHours(2).plusMinutes(21),
            volumeYes = 5.6f,
            volumeNo = 4.4f,
            volume = 10.0f,
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
        )
    )
}