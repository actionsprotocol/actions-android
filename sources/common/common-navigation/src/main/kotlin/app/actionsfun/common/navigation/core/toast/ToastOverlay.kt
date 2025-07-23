package app.actionsfun.common.navigation.core.toast

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import app.actionsfun.common.navigation.core.Destination
import app.actionsfun.common.navigation.core.Navigator

@Suppress("ConstPropertyName")
private object ToastAnimationSpec {
    val SlideIn = tween<IntOffset>(durationMillis = 300, easing = FastOutSlowInEasing)
    val SlideOut = tween<IntOffset>(durationMillis = 250, easing = FastOutLinearInEasing)
    val FadeIn = tween<Float>(durationMillis = 200)
    val FadeOut = tween<Float>(durationMillis = 150)
    const val DisplayDuration = 2000L
    const val TransitionDelay = 150L
}

@Composable
fun ToastOverlay(
    toastState: ToastState,
    navigator: Navigator,
    content: @Composable (Destination.Toast, Navigator) -> Unit
) {
    val activeToast by toastState.activeToast.collectAsState()
    var currentToastItem by remember { mutableStateOf<ToastItem?>(null) }
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(activeToast) {
        val toast = activeToast
        when {
            toast != null && currentToastItem == null -> {
                currentToastItem = toast
                isVisible = true
                delay(ToastAnimationSpec.DisplayDuration)
                toastState.dismiss(toast.id)
            }
            toast != null && currentToastItem != null && toast.id != currentToastItem?.id -> {
                isVisible = false
                delay(ToastAnimationSpec.TransitionDelay)
                currentToastItem = toast
                isVisible = true
                delay(ToastAnimationSpec.DisplayDuration)
                toastState.dismiss(toast.id)
            }
            toast == null && currentToastItem != null -> {
                isVisible = false
                delay(ToastAnimationSpec.SlideOut.durationMillis.toLong())
                currentToastItem = null
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        AnimatedVisibility(
            visible = isVisible && currentToastItem != null,
            enter = slideInVertically(
                animationSpec = ToastAnimationSpec.SlideIn,
                initialOffsetY = { -it }
            ) + fadeIn(
                animationSpec = ToastAnimationSpec.FadeIn
            ),
            exit = slideOutVertically(
                animationSpec = ToastAnimationSpec.SlideOut,
                targetOffsetY = { -it }
            ) + fadeOut(
                animationSpec = ToastAnimationSpec.FadeOut
            )
        ) {
            currentToastItem?.destination?.let { toast ->
                Box(
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(horizontal = 16.dp)
                ) {
                    content(toast, navigator)
                }
            }
        }
    }
}