package app.actionsfun.feature.home.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
internal fun SectionedArcProgress(
    sections: List<Section>,
    modifier: Modifier = Modifier,
    animate: Boolean = true,
    strokeWidth: Dp = 1.dp,
) {
    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(sections, animate) {
        if (animate) {
            animatedProgress.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1000)
            )
        } else {
            animatedProgress.snapTo(1f)
        }
    }

    Canvas(
        modifier = modifier.aspectRatio(2f)
    ) {
        val strokeWidthPx = strokeWidth.toPx()
        val padding = strokeWidthPx / 2
        val arcSize = Size(
            width = size.width - padding * 2,
            height = size.height * 2 - padding * 2
        )

        val totalValue = sections.sumOf { it.value.toDouble() }.toFloat()
        if (totalValue == 0f) return@Canvas

        var currentAngle = 180f

        val gapAngle = strokeWidthPx * 1.5f
        val totalGapAngle = gapAngle * (sections.size - 1)
        val availableAngle = 180f - totalGapAngle

        sections.forEachIndexed { index, section ->
            val sweepAngle = (section.value / totalValue) * availableAngle * animatedProgress.value

            drawArc(
                color = section.color,
                startAngle = currentAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                topLeft = Offset(padding, padding),
                size = arcSize,
                style = Stroke(
                    width = strokeWidthPx,
                    cap = StrokeCap.Round
                )
            )

            currentAngle += sweepAngle
            if (index < sections.size - 1) {
                currentAngle += gapAngle
            }
        }
    }
}

internal data class Section(
    val color: Color,
    val value: Float
)

@Preview
@Composable
private fun Preview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center,
    ) {
        SectionedArcProgress(
            modifier = Modifier
                .width(48.dp),
            strokeWidth = 6.dp,
            sections = listOf(
                Section(
                    color = Color.Green,
                    value = 5.5f,
                ),
                Section(
                    color = Color.Gray,
                    value = 4.5f,
                ),
            )
        )
    }
}