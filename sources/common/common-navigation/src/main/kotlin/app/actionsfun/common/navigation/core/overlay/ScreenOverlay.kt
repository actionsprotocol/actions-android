package app.actionsfun.common.navigation.core.overlay

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import app.actionsfun.common.navigation.core.Destination
import app.actionsfun.common.navigation.core.Navigator

@Composable
fun ScreenOverlay(
    overlayScreenState: OverlayScreenState,
    navigator: Navigator,
    content: @Composable (Destination.Screen, Navigator) -> Unit,
) {
    val currentScreen by overlayScreenState.currentScreen.collectAsState()
    var animatingScreen by remember { mutableStateOf<Destination.Screen?>(null) }
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(currentScreen) {
        when {
            currentScreen != null -> {
                animatingScreen = currentScreen
                isVisible = true
            }
            animatingScreen != null -> {
                isVisible = false
                delay(300)
                animatingScreen = null
            }
        }
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically { it },
        exit = slideOutVertically { it }
    ) {
        animatingScreen?.let { screen ->
            content(screen, navigator)
        }
    }
}