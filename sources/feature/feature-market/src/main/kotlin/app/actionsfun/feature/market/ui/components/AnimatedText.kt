package app.actionsfun.feature.market.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import app.actionsfun.common.ui.style.AppTheme
import app.actionsfun.common.ui.style.Heading1
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun AnimatedNumber(
    number: Number,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    color: Color = Color.Unspecified,
    animationSpec: AnimationSpec<Float> = tween(
        durationMillis = 300,
        easing = EaseInOut
    )
) {
    val numberString = remember(number) {
        when (number) {
            is Float -> number.toString()
            is Double -> number.toString()
            else -> number.toInt().toString()
        }
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        numberString.forEachIndexed { index, char ->
            when {
                char.isDigit() -> {
                    AnimatedDigit(
                        targetDigit = char.digitToInt(),
                        style = style,
                        color = color,
                        animationSpec = animationSpec,
                        delay = (index + 10) * 100L
                    )
                }
                else -> {
                    Text(
                        text = char.toString(),
                        style = style,
                        color = color
                    )
                }
            }
        }
    }
}

@Composable
private fun AnimatedDigit(
    targetDigit: Int,
    style: TextStyle,
    color: Color,
    animationSpec: AnimationSpec<Float>,
    delay: Long = 0L
) {
    var currentDigit by remember { mutableIntStateOf(targetDigit) }
    var oldDigit by remember { mutableIntStateOf(targetDigit) }
    val animatable = remember { Animatable(0f) }
    var digitHeight by remember { mutableIntStateOf(0) }
    var hasAnimated by remember { mutableStateOf(false) }

    LaunchedEffect(targetDigit) {
        if (!hasAnimated) {
            launch {
                delay(delay)
                animatable.snapTo(-1f)
                animatable.animateTo(0f, animationSpec)
                hasAnimated = true
            }
        } else if (currentDigit != targetDigit) {
            oldDigit = currentDigit
            currentDigit = targetDigit

            launch {
                delay(delay)
                animatable.snapTo(0f)
                animatable.animateTo(1f, animationSpec)
            }
        }
    }

    Box(
        modifier = Modifier
            .clipToBounds()
            .onSizeChanged { size ->
                digitHeight = size.height
            }
    ) {
        val progress = animatable.value

        if (!hasAnimated) {
            val offset = progress * digitHeight

            Text(
                text = targetDigit.toString(),
                style = style,
                color = color,
                textAlign = TextAlign.Center,
                modifier = Modifier.offset {
                    IntOffset(0, offset.roundToInt())
                }
            )
        } else {
            val offset = -progress * digitHeight

            Text(
                text = oldDigit.toString(),
                style = style,
                color = color,
                textAlign = TextAlign.Center,
                modifier = Modifier.offset {
                    IntOffset(0, offset.roundToInt())
                }
            )

            Text(
                text = currentDigit.toString(),
                style = style,
                color = color,
                textAlign = TextAlign.Center,
                modifier = Modifier.offset {
                    IntOffset(0, (offset + digitHeight).roundToInt())
                }
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        AnimatedNumber(
            number = 56,
            style = Heading1,
            color = AppTheme.Colors.Text.Primary
        )
    }
}