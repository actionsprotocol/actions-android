package app.actionsfun.common.ui.components.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.actionsfun.common.ui.AppPreview
import app.actionsfun.common.ui.R
import app.actionsfun.common.ui.style.*

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit = {
        Text(
            text = text,
            style = Body14Medium.applyColor(
                if (enabled) AppTheme.Colors.Background.Primary else AppTheme.Colors.Text.Disabled
            ),
            maxLines = 1,
            overflow = TextOverflow.MiddleEllipsis,
            textAlign = TextAlign.Center
        )
    },
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        enabled = enabled,
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = AppTheme.Colors.Text.Primary,
            contentColor = AppTheme.Colors.Core.Primary,
            disabledContainerColor = AppTheme.Colors.Core.Secondary.copy(alpha = 0.5f),
            disabledContentColor = AppTheme.Colors.Text.Disabled
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            hoveredElevation = 0.dp,
            focusedElevation = 0.dp
        )
    ) {
        content()
    }
}

@Composable
fun OutlineIconButton(
    onClick: () -> Unit,
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .width(40.dp)
            .height(32.dp)
            .background(
                color = AppTheme.Colors.Background.Primary,
                shape = RoundedCornerShape(4.dp)
            )
            .border(
                width = 1.dp,
                color = AppTheme.Colors.Border.Secondary,
                shape = RoundedCornerShape(4.dp),
            ),
        enabled = enabled,
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = icon),
            tint = AppTheme.Colors.Text.Tertiary,
            contentDescription = null,
        )
    }
}

@Composable
fun PillButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    @DrawableRes iconStart: Int? = null,
    @DrawableRes iconEnd: Int? = null,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(36.dp),
        enabled = enabled,
        shape = RoundedCornerShape(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black,
            contentColor = Color.White,
            disabledContainerColor = Color.Black.copy(alpha = 0.5f),
            disabledContentColor = AppTheme.Colors.Text.Primary.copy(alpha = 0.5f)
        ),
        contentPadding = PaddingValues(horizontal = 16.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            hoveredElevation = 0.dp,
            focusedElevation = 0.dp
        )
    ) {
        iconStart?.let { icon ->
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = icon),
                tint = AppTheme.Colors.Text.Primary,
                contentDescription = null,
            )

            Spacer(modifier = Modifier.width(6.dp))
        }
        Text(
            text = text,
            style = Body14SemiBold,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        iconEnd?.let { icon ->

            Spacer(modifier = Modifier.width(6.dp))

            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = icon),
                tint = AppTheme.Colors.Text.Primary,
                contentDescription = null,
            )
        }
    }
}

@Composable
fun ThreeDimensionalButton(
    text: String,
    color: Color,
    shadowColor: Color,
    modifier: Modifier = Modifier,
    shadowAlignment: Alignment = Alignment.BottomCenter,
    onClick: () -> Unit = { }
) {
    BaseThreeDimensionalButton(
        color = color,
        shadowColor = shadowColor,
        modifier = modifier,
        shadowAlignment = shadowAlignment,
        onClick = onClick,
    ) {
        Text(
            text = text,
            color = Color(0xFFF8F9FA),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
internal fun BaseThreeDimensionalButton(
    color: Color,
    shadowColor: Color,
    modifier: Modifier = Modifier,
    shadowAlignment: Alignment = Alignment.BottomCenter,
    shape: Shape = RoundedCornerShape(32.dp),
    onClick: () -> Unit = { },
    content: @Composable () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val shadowDepth by animateDpAsState(
        targetValue = 4.dp,
        animationSpec = tween(durationMillis = 100),
        label = "shadowDepth"
    )

    val buttonOffset by animateDpAsState(
        targetValue = if (isPressed) 4.dp else 0.dp,
        animationSpec = tween(durationMillis = 100),
        label = "buttonOffset"
    )

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

    val (buttonPressOffsetX, buttonPressOffsetY) = when (shadowAlignment) {
        Alignment.TopStart -> -buttonOffset to -buttonOffset
        Alignment.TopCenter -> 0.dp to -buttonOffset
        Alignment.TopEnd -> buttonOffset to -buttonOffset
        Alignment.CenterStart -> -buttonOffset to 0.dp
        Alignment.Center -> 0.dp to 0.dp
        Alignment.CenterEnd -> buttonOffset to 0.dp
        Alignment.BottomStart -> -buttonOffset to buttonOffset
        Alignment.BottomCenter -> 0.dp to buttonOffset
        Alignment.BottomEnd -> buttonOffset to buttonOffset
        else -> 0.dp to buttonOffset
    }

    Box(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(x = shadowOffsetX, y = shadowOffsetY)
                .clip(shape)
                .background(shadowColor)
                .clickable { onClick() }
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(x = buttonPressOffsetX, y = buttonPressOffsetY)
                .clip(shape)
                .background(color)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClick
                )
                .padding(horizontal = 24.dp, vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

@AppPreview
@Composable
private fun Preview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppTheme.Colors.Background.Primary)
            .padding(horizontal = 24.dp, vertical = 56.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PrimaryButton(
            text = "Primary Button",
            onClick = {},
        )

        OutlineIconButton(
            icon = R.drawable.ic_eye,
            onClick = {},
        )

        ThreeDimensionalButton(
            text = "YES",
            color = Color(0xFF4CAF50),
            shadowColor = Color(0xFF2E7D32),
            shadowAlignment = Alignment.BottomCenter,
            onClick = {}
        )
    }
}
