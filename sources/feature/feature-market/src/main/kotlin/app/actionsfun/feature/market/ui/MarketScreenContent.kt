package app.actionsfun.feature.market.ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.actionsfun.common.ui.browser.openUrlInCustomTab
import app.actionsfun.common.ui.components.market.MarketShimmer
import app.actionsfun.common.ui.components.pager.PagerStateObserver
import app.actionsfun.common.ui.style.AppTheme
import app.actionsfun.feature.market.ui.components.market.Deposit
import app.actionsfun.feature.market.ui.components.market.Market
import app.actionsfun.feature.market.ui.components.market.RepliesCard
import app.actionsfun.feature.market.ui.components.market.Video
import kotlinx.coroutines.launch

@Composable
internal fun MarketScreenContent(
    state: MarketUIState,
    modifier: Modifier = Modifier,
    isVerticallyVisible: Boolean = true,
    depositQuickAmountClick: (Float) -> Unit = { Unit },
    depositOptionClick: (Boolean) -> Unit = { Unit },
    depositValueChange: (Float) -> Unit = { Unit },
    depositActionButtonClick: () -> Unit = { Unit },
    onSendReply: (String) -> Unit = { Unit },
    onConnectWallet: () -> Unit = { Unit },
) {
    val pagerState = rememberPagerState { state.pages }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    fun scrollToPage(page: Int) {
        coroutineScope.launch {
            if (page in 0..<state.pages) {
                pagerState.animateScrollToPage(page)
            }
        }
    }

    fun openUrl(url: String) {
        context.openUrlInCustomTab(url)
    }

    LaunchedEffect(state.selectedCard) {
        pagerState.animateScrollToPage(state.selectedCard)
    }

    Column(
        modifier = modifier
            .background(AppTheme.Colors.Background.Primary)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val keyboard = LocalSoftwareKeyboardController.current

        PagerStateObserver(
            pagerState = pagerState,
        ) { _, _ -> keyboard?.hide() }

        PagerIndicator(
            pagerState = pagerState,
            accentColor = state.market?.accentColor ?: AppTheme.Colors.Border.Secondary,
        )

        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            pageSpacing = 16.dp,
            userScrollEnabled = state.market != null,
            modifier = Modifier.weight(1f),
        ) { page ->
            when (page) {
                state.videoIndex -> {
                    state.video?.let { video ->
                        Video(
                            state = video,
                            playWhenReady = isVerticallyVisible && (pagerState.currentPage == page),
                            buttonClick = {
                                scrollToPage(state.depositIndex)
                            }
                        )
                    }
                }
                state.marketInfoIndex -> {
                    Crossfade(
                        modifier = modifier,
                        targetState = state.market,
                        animationSpec = tween(durationMillis = 300)
                    ) { market ->
                        if (market != null) {
                            Market(
                                state = market,
                                profileClick = ::openUrl,
                                viewAllRepliesClick = {
                                    scrollToPage(state.repliesIndex)
                                },
                                buttonClick = {
                                    scrollToPage(state.depositIndex)
                                },
                            )
                        } else {
                            MarketShimmer(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(bottom = 84.dp),
                            )
                        }
                    }
                }
                state.depositIndex -> {
                    Deposit(
                        state = state.deposit,
                        quickAmountClick = depositQuickAmountClick,
                        optionClick = depositOptionClick,
                        valueChange = depositValueChange,
                        actionButtonClick = depositActionButtonClick,
                    )
                }
                state.repliesIndex -> {
                    RepliesCard(
                        state = state.replies,
                        onSendReply = onSendReply,
                        onConnectWallet = onConnectWallet,
                    )
                }
            }
        }
    }
}

@Composable
private fun PagerIndicator(
    pagerState: PagerState,
    accentColor: Color,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(pagerState.pageCount) { index ->
            val isSelected = pagerState.currentPage == index
            val color by animateColorAsState(
                targetValue = if (isSelected) {
                    accentColor
                } else {
                    Color(0xFFEEEFF1)
                },
                animationSpec = tween(300),
                label = "AnimatedPagerIndicator.Color"
            )

            Box(
                modifier = Modifier
                    .width(24.dp)
                    .height(6.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(color)
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
//    MarketScreenContent()
}