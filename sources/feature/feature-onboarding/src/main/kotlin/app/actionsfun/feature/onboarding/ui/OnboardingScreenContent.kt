package app.actionsfun.feature.onboarding.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import app.actionsfun.common.ui.AppPreview
import app.actionsfun.common.ui.components.button.ThreeDimensionalButton
import app.actionsfun.feature.onboarding.presentation.model.OnboardingButton
import app.actionsfun.feature.onboarding.presentation.model.OnboardingState
import mx.platacard.pagerindicator.PagerIndicatorOrientation
import mx.platacard.pagerindicator.PagerWormIndicator


@Composable
internal fun OnboardingScreenContent(
    state: OnboardingState,
    modifier: Modifier = Modifier,
    onClick: (OnboardingButton) -> Unit = { Unit },
) {
    val pagerState = rememberPagerState { state.screens.size }
    val currentBackgroundColor by remember {
        derivedStateOf {
            val currentPage = pagerState.currentPage
            val pageOffset = pagerState.currentPageOffsetFraction

            when {
                pageOffset > 0 && currentPage < state.screens.size - 1 -> {
                    val currentColor = state.screens[currentPage].color
                    val nextColor = state.screens[currentPage + 1].color
                    lerp(currentColor, nextColor, pageOffset)
                }

                pageOffset < 0 && currentPage > 0 -> {
                    val currentColor = state.screens[currentPage].color
                    val prevColor = state.screens[currentPage - 1].color
                    lerp(currentColor, prevColor, -pageOffset)
                }

                else -> state.screens[currentPage].color
            }
        }
    }
    val backgroundColor by animateColorAsState(
        targetValue = currentBackgroundColor,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
        label = "Splash.BackgroundColor"
    )

    LaunchedEffect(state.selectedScreen) {
        pagerState.animateScrollToPage(state.selectedScreen)
    }

    Box(
        modifier = modifier
            .background(backgroundColor)
            .fillMaxSize(),
    ) {
        Toolbar(
            pagerState = pagerState,
            modifier = Modifier
                .statusBarsPadding(),
        )
        HorizontalPager(
            modifier = Modifier
                .fillMaxSize(),
            userScrollEnabled = false,
            overscrollEffect = null,
            state = pagerState,
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.FillBounds,
                    painter = painterResource(state.screens[page].image),
                    contentDescription = null,
                )
                Footer(
                    modifier = Modifier
                        .padding(all = 24.dp)
                        .align(Alignment.BottomCenter),
                    state = state.screens[page].button,
                    onClick = onClick,
                )
            }
        }
    }
}

@Composable
private fun Toolbar(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .height(64.dp)
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.CenterEnd,
    ) {
        PagerWormIndicator(
            pagerState = pagerState,
            activeDotColor = Color.Black,
            dotColor = Color(0xFF4E4949).copy(alpha = .4f),
            dotCount = pagerState.pageCount,
            activeDotSize = 8.dp,
            minDotSize = 8.dp,
            orientation = PagerIndicatorOrientation.Horizontal
        )
    }
}

@Composable
private fun Footer(
    state: OnboardingButton,
    modifier: Modifier = Modifier,
    onClick: (OnboardingButton) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        ThreeDimensionalButton(
            modifier = Modifier
                .fillMaxSize(),
            text = state.text,
            color = Color.Black,
            shadowColor = Color(0xFF7F7F7F),
            onClick = { onClick(state) }
        )
    }
}

@AppPreview
@Composable
private fun Preview() {
    OnboardingScreenContent(
        state = OnboardingState.Default,
    )
}