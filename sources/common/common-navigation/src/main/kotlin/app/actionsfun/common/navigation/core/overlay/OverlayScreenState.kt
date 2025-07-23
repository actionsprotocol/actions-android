package app.actionsfun.common.navigation.core.overlay

import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import app.actionsfun.common.navigation.core.Destination

@Stable
class OverlayScreenState internal constructor() {
    private val state = MutableStateFlow<Destination.Screen?>(null)

    val currentScreen: StateFlow<Destination.Screen?> = state.asStateFlow()

    internal fun show(screen: Destination.Screen) {
        state.value = screen
    }

    internal fun hide() {
        state.value = null
    }
}