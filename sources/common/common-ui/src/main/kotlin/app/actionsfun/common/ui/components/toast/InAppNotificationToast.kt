package app.actionsfun.common.ui.components.toast

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import app.actionsfun.common.ui.AppPreview
import app.actionsfun.common.ui.R
import app.actionsfun.common.ui.style.AppTheme
import app.actionsfun.common.ui.style.Body14Regular

@Composable
fun InAppNotificationToast(
    message: String,
    color: Color,
    icon: Painter,
) {
    Row(
        modifier = Modifier
            .background(
                color = AppTheme.Colors.Background.Primary,
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.dp,
                color = color,
                shape = RoundedCornerShape(16.dp)
            )
            .heightIn(min = 72.dp)
            .fillMaxWidth()
            .padding(all = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(18.dp)
                .background(
                    color = color,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
           Icon(
               modifier = Modifier
                   .size(12.dp),
               painter = icon,
               tint = Color.White,
               contentDescription = null,
           )
        }

        Text(
            text = message,
            style = Body14Regular,
            color = AppTheme.Colors.Text.Primary,
        )
    }
}

@Composable
@AppPreview
private fun Preview() {
    val density = LocalDensity.current
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF334BF4),
                            Color(0xFF000119),
                        ),
                        end = Offset(x = 0f, y = with(density) { maxHeight.toPx() * 0.7f })
                    )
                )
        ) {
            InAppNotificationToast(
                message = "An error occurred",
                color = Color.Red,
                icon = painterResource(R.drawable.ds_circle_help)
            )
        }
    }
}
