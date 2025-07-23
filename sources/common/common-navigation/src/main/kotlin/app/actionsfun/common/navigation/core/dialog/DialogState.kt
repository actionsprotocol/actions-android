package app.actionsfun.common.navigation.core.dialog

import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import app.actionsfun.common.navigation.core.Destination

@Stable
class DialogState internal constructor() {
    private val state = MutableStateFlow<Destination.Dialog?>(null)

    val currentDialog: StateFlow<Destination.Dialog?> = state.asStateFlow()

    internal fun show(dialog: Destination.Dialog) {
        state.value = dialog
    }

    internal fun hide() {
        state.value = null
    }
}