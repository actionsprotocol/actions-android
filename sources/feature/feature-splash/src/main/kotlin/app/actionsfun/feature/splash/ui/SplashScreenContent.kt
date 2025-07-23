package app.actionsfun.feature.splash.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.actionsfun.common.ui.AppPreview
import app.actionsfun.common.ui.components.ascii.AsciiDVPN
import app.actionsfun.common.ui.style.AppTheme
import app.actionsfun.common.ui.style.Body12Regular
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

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

    }
}

@AppPreview
@Composable
private fun Preview() {
    SplashScreenContent()
}
