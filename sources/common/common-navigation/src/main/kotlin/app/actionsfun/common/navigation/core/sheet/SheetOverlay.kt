package app.actionsfun.common.navigation.core.sheet

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import app.actionsfun.common.navigation.core.Destination
import app.actionsfun.common.navigation.core.Navigator
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.util.VelocityTracker

@Suppress("ConstPropertyName")
private object SheetAnimationSpec {
    val ScrimFadeIn = tween<Float>(durationMillis = 450, easing = FastOutSlowInEasing)
    val ScrimFadeOut = tween<Float>(durationMillis = 350, easing = FastOutLinearInEasing)

    val SnapBack = spring<Float>(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessMediumLow
    )

    val Dismiss = tween<Float>(durationMillis = 350, easing = FastOutLinearInEasing)

    const val VelocityThreshold = 800f
    const val DragThreshold = 150f
    const val MaxDragForAlpha = 400f
    const val MaxScrimAlpha = 0.6f
}

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun SheetOverlay(
    sheetState: SheetState,
    navigator: Navigator,
    content: @Composable (Destination.Sheet, Navigator) -> Unit
) {
    val currentSheet by sheetState.currentSheet.collectAsState()

    currentSheet?.let { sheet ->
        val density = LocalDensity.current
        val initialOffset = with(density) { 1000.dp.toPx() }
        val offsetY = remember { Animatable(initialOffset) }
        val scrimAlpha = remember { Animatable(0f) }
        val scope = rememberCoroutineScope()

        LaunchedEffect(sheet) {
            launch {
                scrimAlpha.animateTo(
                    targetValue = SheetAnimationSpec.MaxScrimAlpha,
                    animationSpec = SheetAnimationSpec.ScrimFadeIn
                )
            }
            offsetY.animateTo(
                targetValue = 0f,
                animationSpec = tween(
                    durationMillis = 500,
                    easing = CubicBezierEasing(0.05f, 0.7f, 0.1f, 1f)
                )
            )
        }

        fun dismiss() {
            scope.launch {
                launch {
                    scrimAlpha.animateTo(
                        targetValue = 0f,
                        animationSpec = SheetAnimationSpec.ScrimFadeOut
                    )
                }
                offsetY.animateTo(
                    targetValue = with(density) { 1200.dp.toPx() },
                    animationSpec = SheetAnimationSpec.Dismiss
                )
                navigator.back()
            }
        }

        BackHandler(enabled = true) {
            dismiss()
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = scrimAlpha.value))
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        dismiss()
                    }
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = with(density) { offsetY.value.toDp() })
                    .pointerInput(Unit) {
                        val velocityTracker = VelocityTracker()
                        detectVerticalDragGestures(
                            onDragEnd = {
                                val velocity = velocityTracker.calculateVelocity().y
                                val currentOffset = offsetY.value
                                val threshold = with(density) { SheetAnimationSpec.DragThreshold.dp.toPx() }

                                scope.launch {
                                    if (velocity > SheetAnimationSpec.VelocityThreshold || currentOffset > threshold) {
                                        dismiss()
                                    } else {
                                        launch {
                                            offsetY.animateTo(
                                                targetValue = 0f,
                                                animationSpec = SheetAnimationSpec.SnapBack
                                            )
                                        }
                                        scrimAlpha.animateTo(
                                            targetValue = SheetAnimationSpec.MaxScrimAlpha,
                                            animationSpec = SheetAnimationSpec.ScrimFadeIn
                                        )
                                    }
                                }
                            }
                        ) { change, _ ->
                            velocityTracker.addPosition(change.uptimeMillis, change.position)

                            scope.launch {
                                val newOffset = offsetY.value + change.position.y - change.previousPosition.y
                                if (newOffset >= 0) {
                                    offsetY.snapTo(newOffset)
                                    val progress = 1f - (newOffset / with(density) { SheetAnimationSpec.MaxDragForAlpha.dp.toPx() }).coerceIn(0f, 1f)
                                    scrimAlpha.snapTo(SheetAnimationSpec.MaxScrimAlpha * progress)
                                }
                            }
                        }
                    }
            ) {
                content(sheet, navigator)
            }
        }
    }
}
