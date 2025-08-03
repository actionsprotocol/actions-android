package app.actionsfun.feature.market.ui.components.market

import android.annotation.SuppressLint
import android.media.MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import app.actionsfun.common.ui.avatar.avatarByWalletAddress
import app.actionsfun.common.ui.components.button.PrimaryButton
import app.actionsfun.common.ui.components.threed.ThreeDimensionalLayout
import app.actionsfun.common.ui.exoplayer.ExoPlayer
import app.actionsfun.common.ui.market.MarketStatusUI
import app.actionsfun.common.ui.style.AppTheme
import app.actionsfun.common.ui.style.Body12Regular
import app.actionsfun.common.ui.style.Body14Medium
import app.actionsfun.common.ui.style.Body16Medium
import app.actionsfun.common.util.timeRelativeString
import app.actionsfun.feature.market.ui.VideoUI
import java.time.OffsetDateTime

@SuppressLint("UnsafeOptInUsageError")
@Composable
internal fun Video(
    state: VideoUI,
    modifier: Modifier = Modifier,
    playWhenReady: Boolean = true,
    buttonClick: () -> Unit = { Unit },
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ThreeDimensionalLayout(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = AppTheme.Colors.Background.Surface,
            shape = RoundedCornerShape(32.dp),
            shadowColor = Color(0xFFEC58A9),
            shadowAlignment = Alignment.BottomStart,
        ) {
            state.videoUrl?.let { video ->
                ExoPlayer(
                    source = video.toUri(),
                    playWhenReady = playWhenReady,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(32.dp))
                ) {
                    setForegroundMode(false)
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.Black,
                        shape = RoundedCornerShape(
                            bottomStart = 24.dp,
                            bottomEnd = 24.dp
                        )
                    )
                    .align(Alignment.BottomCenter)
                    .padding(all = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Image(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        painter = painterResource(state.creatorUsername.avatarByWalletAddress),
                        contentDescription = null,
                    )

                    Column(
                        modifier = Modifier
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = state.creatorUsername,
                            style = Body14Medium,
                            color = Color.White
                        )
                        Text(
                            text = state.createdAt.timeRelativeString(),
                            style = Body12Regular,
                            color = AppTheme.Colors.Text.Tertiary
                        )
                    }

                    app.actionsfun.common.ui.market.MarketStatus(
                        state = state.marketStatusUI,
                        textColor = Color.White,
                    )
                }

                Text(
                    text = state.title,
                    style = Body16Medium,
                    color = Color.White,
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

@Preview
@Composable
private fun Preview() {
    Video(
        modifier = Modifier
            .fillMaxSize(),
        state = VideoUI(
            title = "Will pump.fun have higher trading volume than bonk.fun in exactly 24 hours?",
            creatorUsername = "narracanz",
            createdAt = OffsetDateTime.now(),
            marketStatusUI = MarketStatusUI.Deciding,
            videoUrl = null,
            button = "Trade",
        )
    )
}