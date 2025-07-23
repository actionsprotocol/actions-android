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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.actionsfun.common.ui.AppPreview
import app.actionsfun.common.ui.R
import app.actionsfun.common.ui.style.*


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
    }
}