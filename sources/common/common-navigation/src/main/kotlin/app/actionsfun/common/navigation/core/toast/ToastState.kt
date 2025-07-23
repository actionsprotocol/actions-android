package app.actionsfun.common.navigation.core.toast

import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import app.actionsfun.common.navigation.core.Destination

@Stable
class ToastState internal constructor() {
    private val queueState = MutableStateFlow<List<ToastItem>>(emptyList())
    val queue: StateFlow<List<ToastItem>> = queueState.asStateFlow()

    private val activeToastState = MutableStateFlow<ToastItem?>(null)
    val activeToast: StateFlow<ToastItem?> = activeToastState.asStateFlow()

    internal fun show(toast: Destination.Toast) {
        val newToast = ToastItem(destination = toast)
        queueState.update { it + newToast }

        if (activeToastState.value == null) {
            showNext()
        }
    }

    internal fun dismiss(toastId: String) {
        when (activeToastState.value?.id) {
            toastId -> {
                activeToastState.value = null
                showNext()
            }
            else -> queueState.update { queue ->
                queue.filterNot { it.id == toastId }
            }
        }
    }

    private fun showNext() {
        val currentQueue = queueState.value

        when {
            currentQueue.isEmpty() -> activeToastState.value = null
            else -> {
                activeToastState.value = currentQueue.first()
                queueState.update { it.drop(1) }
            }
        }
    }
}