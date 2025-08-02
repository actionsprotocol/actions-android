package app.actionsfun.common.ui.components.button

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.actionsfun.common.ui.style.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview


enum class ButtonStyle {
    Pink,
    Black,
    Green,
    Red
}

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: ButtonStyle = ButtonStyle.Pink,
    enabled: Boolean = true,
    loading: Boolean = false,
    clickable: Boolean = true,
    content: @Composable RowScope.() -> Unit = {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = when (style) {
                    ButtonStyle.Black -> Color.White
                    else -> Color.White
                },
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = text,
                style = Body14Medium.applyColor(
                    when {
                        !enabled -> Color.White.copy(alpha = .4f)
                        style == ButtonStyle.Black -> Color.White
                        else -> Color.White
                    }
                ),
                maxLines = 1,
                overflow = TextOverflow.MiddleEllipsis,
                textAlign = TextAlign.Center
            )
        }
    },
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val buttonColor = when (style) {
        ButtonStyle.Pink -> Color(0xFFEC58A9)
        ButtonStyle.Black -> Color(0xFF000000)
        ButtonStyle.Green -> Color(0xFF21D979)
        ButtonStyle.Red -> Color(0xFFFF5079)
    }

    val shadowColor = buttonColor.copy(alpha = .4f)

    val shadowOffsetY by animateDpAsState(
        targetValue = if (isPressed) 0.dp else 4.dp,
        animationSpec = tween(durationMillis = 50),
        label = "shadowOffsetY"
    )

    val buttonOffsetY by animateDpAsState(
        targetValue = if (isPressed) 4.dp else 0.dp,
        animationSpec = tween(durationMillis = 50),
        label = "buttonOffsetY"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
    ) {
        if (enabled && shadowOffsetY > 0.dp) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .offset(y = shadowOffsetY)
                    .background(
                        color = shadowColor,
                        shape = RoundedCornerShape(24.dp)
                    )
            )
        }

        Surface(
            onClick = { if (enabled && clickable && !loading) onClick() },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .offset(y = buttonOffsetY),
            enabled = enabled && clickable && !loading,
            shape = RoundedCornerShape(24.dp),
            color = if (enabled) buttonColor else AppTheme.Colors.Core.Secondary.copy(alpha = 0.5f),
            interactionSource = interactionSource
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                content()
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        PrimaryButton(
            text = "Continue",
            onClick = { Unit },
            style = ButtonStyle.Green
        )

        PrimaryButton(
            text = "Delete",
            onClick = { Unit },
            style = ButtonStyle.Red
        )

        PrimaryButton(
            text = "Submit",
            onClick = { Unit },
            style = ButtonStyle.Pink
        )

        PrimaryButton(
            text = "Dark Mode",
            onClick = { Unit },
            style = ButtonStyle.Black
        )

        PrimaryButton(
            text = "Loading...",
            onClick = { Unit },
            style = ButtonStyle.Green,
            loading = true
        )

        PrimaryButton(
            text = "Disabled",
            onClick = { Unit },
            style = ButtonStyle.Pink,
            enabled = false
        )
    }
}