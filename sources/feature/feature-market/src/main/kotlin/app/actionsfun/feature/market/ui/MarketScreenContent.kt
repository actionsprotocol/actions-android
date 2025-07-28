package app.actionsfun.feature.market.ui

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.actionsfun.common.ui.style.AppTheme
import app.actionsfun.feature.market.ui.components.market.Market

@Composable
internal fun MarketScreenContent(
    state: MarketUIState,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState { state.pages }

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
        PagerIndicator(
            pagerState = pagerState,
            accentColor = state.market.accentColor,
        )

        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier.weight(1f),
        ) { page ->
            when (page) {
                state.videoIndex -> {

                }
                state.marketInfoIndex -> {
                    Market(state.market)
                }
                state.depositIndex -> {

                }
                state.repliesIndex -> {

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