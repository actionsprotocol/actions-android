package app.actionsfun.common.ui.components.threed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ThreeDimensionalLayout(
    color: Color,
    shadowColor: Color,
    modifier: Modifier = Modifier,
    shadowAlignment: Alignment = Alignment.BottomCenter,
    shadowDepth: Dp = 4.dp,
    shape: Shape = RoundedCornerShape(16.dp),
    contentPadding: Dp = 16.dp,
    content: @Composable () -> Unit,
) {
    val (shadowOffsetX, shadowOffsetY) = when (shadowAlignment) {
        Alignment.TopStart -> -shadowDepth to -shadowDepth
        Alignment.TopCenter -> 0.dp to -shadowDepth
        Alignment.TopEnd -> shadowDepth to -shadowDepth
        Alignment.CenterStart -> -shadowDepth to 0.dp
        Alignment.Center -> 0.dp to 0.dp
        Alignment.CenterEnd -> shadowDepth to 0.dp
        Alignment.BottomStart -> -shadowDepth to shadowDepth
        Alignment.BottomCenter -> 0.dp to shadowDepth
        Alignment.BottomEnd -> shadowDepth to shadowDepth
        else -> 0.dp to shadowDepth
    }

    Box(
        modifier = modifier
            .padding(shadowDepth)
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(x = shadowOffsetX, y = shadowOffsetY)
                .clip(shape)
                .background(shadowColor)
        )

        Box(
            modifier = Modifier
                .clip(shape)
                .background(color)
                .padding(contentPadding)
        ) {
            content()
        }
    }
}
