package app.actionsfun.common.navigation.core.sheet

import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import app.actionsfun.common.navigation.core.Destination

@Stable
class SheetState internal constructor() {
    private val state = MutableStateFlow<Destination.Sheet?>(null)

    val currentSheet: StateFlow<Destination.Sheet?> = state.asStateFlow()

    internal fun show(sheet: Destination.Sheet) {
        state.value = sheet
    }

    internal fun hide() {
        state.value = null
    }
}
