package app.actionsfun.common.ui.style

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val AppColorPalette = lightColorScheme(
    primary = AppColorsLight.Core.Primary,
    secondary = AppColorsLight.Core.Secondary,
    background = AppColorsLight.Background.Primary,
    surface = AppColorsLight.Background.Primary,
    onPrimary = AppColorsLight.Text.Primary,
    onSecondary = AppColorsLight.Text.Primary,
    onBackground = AppColorsLight.Text.Primary,
    onSurface = AppColorsLight.Text.Primary,
)

private val AppColorPaletteDark = darkColorScheme(
    primary = AppColorsDark.Core.Primary,
    secondary = AppColorsDark.Core.Secondary,
    background = AppColorsDark.Background.Primary,
    surface = AppColorsDark.Background.Primary,
    onPrimary = AppColorsDark.Text.Primary,
    onSecondary = AppColorsDark.Text.Primary,
    onBackground = AppColorsDark.Text.Primary,
    onSurface = AppColorsDark.Text.Primary,
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) AppColorPaletteDark else AppColorPalette

    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(8.dp),
        large = RoundedCornerShape(16.dp),
        extraLarge = RoundedCornerShape(24.dp)
    )

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = shapes,
        content = content
    )
}

object AppTheme {
    val Colors: AppColors
        @Composable
        get() = if (isSystemInDarkTheme()) AppColorsDark else AppColorsLight
}