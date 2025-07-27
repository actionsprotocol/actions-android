package app.actionsfun.feature.splash.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import app.actionsfun.common.ui.AppPreview
import app.actionsfun.common.ui.style.AppTheme
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp


@Composable
internal fun SplashScreenContent(
    modifier: Modifier = Modifier,
    onAnimationFinished: () -> Unit = { Unit },
) {
    LaunchedEffect(Unit) {
        delay(2.seconds)
        onAnimationFinished()
    }

    Box(
        modifier = modifier
            .background(AppTheme.Colors.Background.Primary)
            .fillMaxSize(),
    ) {
        SpinningLetterAnimation()
    }
}

@AppPreview
@Composable
private fun Preview() {
    SplashScreenContent()
}

@Composable
fun SpinningLetterAnimation() {
    val text = "actions.fun"
    val letters = text.toCharArray()

    var visibleLetters by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        delay(250)
        for (i in letters.indices) {
            visibleLetters = i + 1
            delay(50)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Row {
            letters.forEachIndexed { index, letter ->
                AnimatedLetter(
                    letter = letter,
                    isVisible = index < visibleLetters,
                    delay = index * 100
                )
            }
        }
    }
}

@Composable
fun AnimatedLetter(
    letter: Char,
    isVisible: Boolean,
    delay: Int
) {
    val transition = updateTransition(
        targetState = isVisible,
        label = "letter-$letter"
    )

    val rotation by transition.animateFloat(
        transitionSpec = {
            if (targetState) {
                tween(
                    durationMillis = 450,
                    easing = FastOutSlowInEasing
                )
            } else {
                tween(0)
            }
        },
        label = "rotation"
    ) { visible ->
        if (visible) 0f else -720f
    }

    val scale by transition.animateFloat(
        transitionSpec = {
            if (targetState) {
                spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            } else {
                tween(0)
            }
        },
        label = "scale"
    ) { visible ->
        if (visible) 1f else 0f
    }

    val alpha by transition.animateFloat(
        transitionSpec = {
            if (targetState) {
                tween(300)
            } else {
                tween(0)
            }
        },
        label = "alpha"
    ) { visible ->
        if (visible) 1f else 0f
    }

    Text(
        text = letter.toString(),
        fontSize = 48.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .graphicsLayer {
                rotationZ = rotation
                scaleX = scale
                scaleY = scale
                this.alpha = alpha
            }
    )
}

@Composable
@Preview
fun OnboardingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
         SpinningLetterAnimation()
    }
}