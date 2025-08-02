@file:OptIn(ExperimentalFoundationApi::class)

package app.actionsfun.feature.profile.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.actionsfun.common.ui.avatar.avatarByWalletAddress
import app.actionsfun.common.ui.components.button.PillButton
import app.actionsfun.common.ui.components.button.ThreeDimensionalButton
import app.actionsfun.common.ui.modifier.bouncingClickable
import app.actionsfun.common.ui.rememberAppShimmer
import app.actionsfun.common.ui.style.AppTheme
import app.actionsfun.common.ui.style.Body12Regular
import app.actionsfun.common.ui.style.Body14Regular
import app.actionsfun.common.ui.style.Body18Medium
import app.actionsfun.common.ui.style.Heading2
import app.actionsfun.common.ui.style.Heading3
import com.valentinilk.shimmer.shimmer
import app.actionsfun.feature.profile.ui.components.Market

@Composable
internal fun ProfileScreenContent(
    state: ProfileUIState,
    modifier: Modifier = Modifier,
    backClick: () -> Unit = { Unit },
    connectWalletClick: () -> Unit = { Unit },
    publicKeyClick: () -> Unit = { Unit },
    urlClick: (String) -> Unit = { Unit },
    retryClick: () -> Unit = { Unit },
    claimClick: (String) -> Unit = { Unit },
) {
    Box(
        modifier = modifier
            .background(AppTheme.Colors.Background.Primary)
            .fillMaxSize()
    ) {
        Toolbar(
            state = state,
            modifier = Modifier
                .statusBarsPadding(),
            backClick = backClick,
            urlClick = urlClick,
        )
        Column(
            modifier = Modifier
                .padding(top = 112.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Header(
                state = state,
                connectWalletClick = connectWalletClick,
                publicKeyClick = publicKeyClick,
            )

            HowItWorks(
                state = state,
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                onClick = urlClick,
            )

            Markets(
                state = state,
                modifier = Modifier,
                retryClick = retryClick,
                claimClick = claimClick,
            )
        }
    }
}

@Composable
private fun Toolbar(
    state: ProfileUIState,
    modifier: Modifier = Modifier,
    backClick: () -> Unit = { Unit },
    urlClick: (String) -> Unit = { Unit },
) {
    Row(
        modifier = modifier
            .height(80.dp)
            .fillMaxWidth()
            .padding(end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = backClick,
        ) {
            Icon(
                modifier = Modifier,
                painter = painterResource(app.actionsfun.common.ui.R.drawable.ic_chevron_left),
                tint = AppTheme.Colors.Text.Primary,
                contentDescription = null,
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CircleLinkButton(
                icon = app.actionsfun.common.ui.R.drawable.ic_github,
                url = state.socials.github,
                onClick = urlClick,
            )
            CircleLinkButton(
                icon = app.actionsfun.common.ui.R.drawable.ic_x,
                url = state.socials.twitter,
                onClick = urlClick,
            )
        }
    }
}

@Composable
private fun CircleLinkButton(
    icon: Int,
    url: String,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = { Unit },
) {
    Box(
        modifier = modifier
            .bouncingClickable { onClick(url) }
            .background(
                color = Color(0xFFEC5AAA).copy(alpha = .25f),
                shape = CircleShape,
            )
            .size(36.dp)
            .clip(CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            painter = painterResource(icon),
            tint = Color(0xFFEC5AAA),
            contentDescription = null,
        )
    }
}

@Composable
private fun Header(
    state: ProfileUIState,
    modifier: Modifier = Modifier,
    connectWalletClick: () -> Unit = { Unit },
    publicKeyClick: () -> Unit = { Unit },
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Avatar(
            state = state,
        )

        AnimatedContent(
            targetState = state.wallet,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
        ) { wallet ->
            when (wallet) {
                is WalletState.Connected -> {
                    Row(
                        modifier = Modifier
                            .padding(all = 8.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .bouncingClickable(onClick = publicKeyClick),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = wallet.publicKey,
                            style = Heading2,
                            color = AppTheme.Colors.Text.Primary,
                        )
                        Icon(
                            modifier = Modifier
                                .rotate(-90f),
                            painter = painterResource(app.actionsfun.common.ui.R.drawable.ic_chevron_left),
                            contentDescription = null,
                        )
                    }
                }
                is WalletState.NotConnected -> {
                    PillButton(
                        text = wallet.connectButton,
                        onClick = connectWalletClick,
                    )
                }
            }
        }
    }
}

@Composable
private fun Avatar(
    state: ProfileUIState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = Modifier
            .size(84.dp)
            .shadow(
                elevation = 84.dp,
                shape = CircleShape,
                ambientColor = Color(0xFFEC5AAA).copy(alpha = .55f),
                spotColor = Color(0xFFEC5AAA).copy(alpha = .55f),
            )
            .background(
                color = Color(0xFFEC5AAA),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = state.wallet.publicKey.avatarByWalletAddress),
            contentDescription = "Pepe",
            modifier = Modifier.size(64.dp)
        )
    }
}

@Composable
private fun HowItWorks(
    state: ProfileUIState,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = { Unit },
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .bouncingClickable { onClick(state.howItWorks.url) }
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF6159FF),
                        Color(0xFF9782FF),
                    )
                ),
                shape = RoundedCornerShape(24.dp),
            )
            .padding(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(
                        color = Color.White.copy(alpha = .2f),
                        shape = CircleShape,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(app.actionsfun.common.ui.R.drawable.app_logo),
                    tint = Color.White,
                    contentDescription = null,
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = state.howItWorks.label,
                    style = Body12Regular,
                    color = Color.White.copy(alpha = .7f),
                )
                Text(
                    text = state.howItWorks.title,
                    style = Body18Medium,
                    color = Color.White.copy(alpha = .7f),
                )
            }
        }

        Text(
            text = state.howItWorks.text,
            style = Body14Regular,
            color = Color.White.copy(alpha = .7f),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun Markets(
    state: ProfileUIState,
    modifier: Modifier = Modifier,
    retryClick: () -> Unit = { Unit },
    claimClick: (String) -> Unit = { Unit },
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = state.marketsSectionTitle,
            style = Heading3,
            color = AppTheme.Colors.Text.Primary,
        )

        AnimatedContent(
            targetState = state.marketsState,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
        ) { marketsState ->
            when (marketsState) {
                is MarketsState.Loading -> {
                    MarketsLoading()
                }
                is MarketsState.Error -> {
                    MarketsError(
                        state = marketsState,
                        onRetryClick = retryClick,
                    )
                }
                is MarketsState.Empty -> {
                    MarketsEmpty(state = marketsState)
                }
                is MarketsState.Content -> {
                    MarketsContent(
                        state = marketsState,
                        claimClick = claimClick,
                    )
                }
            }
        }
    }
}

@Composable
private fun MarketsLoading(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        repeat(3) {
            MarketShimmer()
        }
    }
}

@Composable
private fun MarketShimmer(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shimmer(rememberAppShimmer())
            .border(
                width = 1.dp,
                color = Color(0xFFEBEBEF),
                shape = RoundedCornerShape(24.dp),
            ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Header shimmer
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.Top,
            ) {
                // Title shimmer
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(20.dp)
                        .background(
                            color = AppTheme.Colors.Border.Primary,
                            shape = RoundedCornerShape(4.dp)
                        )
                )
                
                // Status shimmer
                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .height(36.dp)
                        .background(
                            color = AppTheme.Colors.Border.Primary,
                            shape = RoundedCornerShape(24.dp)
                        )
                )
            }
            
            // Chips shimmer
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(3) {
                    Box(
                        modifier = Modifier
                            .width(80.dp)
                            .height(32.dp)
                            .background(
                                color = AppTheme.Colors.Border.Primary,
                                shape = RoundedCornerShape(24.dp)
                            )
                    )
                }
            }
        }
    }
}

@Composable
private fun MarketsError(
    state: MarketsState.Error,
    modifier: Modifier = Modifier,
    onRetryClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp, horizontal = 96.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = state.title,
            style = Body18Medium,
            color = AppTheme.Colors.Text.Primary,
            textAlign = TextAlign.Center,
        )
        
        ThreeDimensionalButton(
            text = state.retryButton,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            color = Color(0xFFEC58A9),
            shadowColor = Color(0xFFEC58A9).copy(alpha = .4f),
            onClick = onRetryClick,
        )
    }
}

@Composable
private fun MarketsEmpty(
    state: MarketsState.Empty,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = state.title,
            style = Body18Medium,
            color = AppTheme.Colors.Text.Primary,
            textAlign = TextAlign.Center,
        )
        
        Text(
            text = state.description,
            style = Body14Regular,
            color = AppTheme.Colors.Text.Secondary,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun MarketsContent(
    state: MarketsState.Content,
    modifier: Modifier = Modifier,
    claimClick: (String) -> Unit = { Unit },
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        state.markets.forEach { market ->
            Market(
                state = market,
                modifier = Modifier.fillMaxWidth(),
                claimClick = claimClick,
            )
        }
    }
}


@Preview
@Composable
private fun Preview() {
    ProfileScreenContent(
        state = ProfileUIState.Preview
    )
}