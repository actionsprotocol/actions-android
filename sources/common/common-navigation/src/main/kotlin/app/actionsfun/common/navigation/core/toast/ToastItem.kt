package app.actionsfun.common.navigation.core.toast

import app.actionsfun.common.navigation.core.Destination
import java.util.UUID

data class ToastItem(
    val id: String = UUID.randomUUID().toString(),
    val destination: Destination.Toast,
    val timestamp: Long = System.currentTimeMillis()
)