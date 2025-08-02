package app.actionsfun.common.ui.modifier

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback

@Composable
fun Modifier.hapticFeedback(
    hapticFeedbackType: HapticFeedbackType = HapticFeedbackType.LongPress,
    enabled: Boolean = true
): Modifier {
    val haptic = LocalHapticFeedback.current

    return this.pointerInput(enabled) {
        detectTapGestures(
            onTap = {
                if (enabled) {
                    haptic.performHapticFeedback(hapticFeedbackType)
                }
            }
        )
    }
}