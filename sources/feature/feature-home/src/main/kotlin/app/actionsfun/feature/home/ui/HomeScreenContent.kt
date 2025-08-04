package app.actionsfun.feature.home.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.actionsfun.common.ui.components.button.PillButton
import app.actionsfun.common.ui.rememberAppShimmer
import app.actionsfun.common.ui.style.AppTheme
import app.actionsfun.common.ui.style.Body14SemiBold
import app.actionsfun.common.ui.style.Body18Medium
import com.valentinilk.shimmer.shimmer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import app.actionsfun.common.navigation.core.Navigator
import app.actionsfun.common.ui.components.button.ThreeDimensionalButton
import app.actionsfun.common.ui.components.market.MarketShimmer
import app.actionsfun.common.ui.components.pager.PagerStateObserver
import app.actionsfun.feature.market.ui.MarketScreen
import org.koin.compose.koinInject

@Composable
internal fun HomeScreenContent(
    state: HomeUIState,
    navigator: Navigator,
    modifier: Modifier = Modifier,
    connectWalletClick: () -> Unit = { Unit },
    profileClick: () -> Unit = { Unit },
    retryLoadingClick: () -> Unit = { Unit },
    onPageChanged: (Int) -> Unit = { Unit },
) {
    Column(
        modifier = modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(AppTheme.Colors.Background.Primary)
            .fillMaxSize(),
    ) {
        Toolbar(
            modifier = Modifier
                .fillMaxWidth(),
            state = state,
            connectWalletClick = connectWalletClick,
            profileClick = profileClick,
        )

        Markets(
            modifier = Modifier
                .fillMaxSize(),
            state = state,
            navigator = navigator,
            retryClick = retryLoadingClick,
            onPageChanged = onPageChanged,
        )
    }
}

@Composable
private fun Toolbar(
    state: HomeUIState,
    modifier: Modifier = Modifier,
    profileClick: () -> Unit = { Unit },
    connectWalletClick: () -> Unit = { Unit },
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Icon(
            modifier = Modifier.size(40.dp),
            tint = AppTheme.Colors.Text.Primary,
            painter = painterResource(app.actionsfun.common.ui.R.drawable.app_logo),
            contentDescription = null,
        )

        AnimatedContent(
            targetState = state,
            transitionSpec = { fadeIn() togetherWith fadeOut() }
        ) { state ->
            when (state) {
                is HomeUIState.Loading -> {
                    Box(
                        modifier = Modifier
                            .width(135.dp)
                            .height(36.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .background(
                                color = Color(0xFFE9E9ED),
                                shape = RoundedCornerShape(24.dp)
                            )
                            .shimmer(rememberAppShimmer())
                    )
                }

                is HomeUIState.Error -> {
                    Wallet(
                        publicKey = state.publicKey,
                        connectWallet = state.connectWallet,
                        connectWalletClick = connectWalletClick,
                        profileClick = profileClick,
                    )
                }

                is HomeUIState.Success -> {
                    Wallet(
                        publicKey = state.publicKey,
                        connectWallet = state.connectWallet,
                        connectWalletClick = connectWalletClick,
                        profileClick = profileClick,
                    )
                }
            }
        }
    }
}

@Composable
private fun Wallet(
    publicKey: String?,
    connectWallet: String,
    connectWalletClick: () -> Unit = { Unit },
    profileClick: () -> Unit = { Unit },
) {
    if (publicKey.isNullOrEmpty()) {
        PillButton(
            text = connectWallet,
            onClick = connectWalletClick,
        )
    } else {
        Row(
            modifier = Modifier
                .width(135.dp)
                .height(36.dp)
                .clip(RoundedCornerShape(24.dp))
                .clickable(onClick = profileClick),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = publicKey,
                color = AppTheme.Colors.Text.Primary,
                style = Body14SemiBold,
                overflow = TextOverflow.MiddleEllipsis,
                maxLines = 1,
            )
            Icon(
                modifier = Modifier.rotate(90f),
                painter = painterResource(app.actionsfun.common.ui.R.drawable.ic_chevron_right),
                tint = AppTheme.Colors.Text.Primary,
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun Markets(
    state: HomeUIState,
    navigator: Navigator,
    modifier: Modifier = Modifier,
    retryClick: () -> Unit = { Unit },
    onPageChanged: (Int) -> Unit = { Unit },
) {
    Crossfade(
        modifier = modifier,
        targetState = state,
        animationSpec = tween(durationMillis = 300)
    ) { marketsState ->
        when (marketsState) {
            is HomeUIState.Loading -> {
                MarketShimmer(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = 16.dp,
                            top = 24.dp,
                            end = 16.dp,
                            bottom = 84.dp
                        ),
                )
            }

            is HomeUIState.Error -> {
                ErrorState(
                    text = marketsState.message,
                    button = marketsState.retryButton,
                    retryClick = retryClick,
                )
            }

            is HomeUIState.Success -> {
                SuccessState(
                    state = marketsState,
                    navigator = navigator,
                    onPageChanged = onPageChanged,
                )
            }
        }
    }
}

@Composable
private fun ErrorState(
    text: String,
    button: String,
    modifier: Modifier = Modifier,
    retryClick: () -> Unit = { Unit },
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            text = text,
            style = Body18Medium,
            textAlign = TextAlign.Center,
            color = AppTheme.Colors.Text.Primary,
        )

        ThreeDimensionalButton(
            modifier = Modifier
                .width(130.dp)
                .height(36.dp),
            text = button,
            color = AppTheme.Colors.Text.Primary,
            shadowColor = AppTheme.Colors.Text.Primary.copy(alpha = 0.4f),
            onClick = retryClick,
        )
    }
}

@Composable
private fun SuccessState(
    state: HomeUIState.Success,
    navigator: Navigator,
    modifier: Modifier = Modifier,
    onPageChanged: (Int) -> Unit = { Unit },
) {
    val pagerState = rememberPagerState { state.markets.size }

    val keyboard = LocalSoftwareKeyboardController.current

    PagerStateObserver(
        pagerState = pagerState,
    ) { _, to ->
        keyboard?.hide()
        onPageChanged(to)
    }

    VerticalPager(
        modifier = modifier
            .fillMaxSize(),
        state = pagerState,
    ) { page ->
        MarketScreen(
            marketAddress = state.markets[page].address,
            navigator = navigator,
            isVerticallyVisible = pagerState.currentPage == page,
            storeProvider = koinInject(),
        )
    }
}

@Preview
@Composable
private fun Preview() {
    HomeScreenContent(
        state = HomeUIState.Default,
        navigator = Navigator.None,
    )
}