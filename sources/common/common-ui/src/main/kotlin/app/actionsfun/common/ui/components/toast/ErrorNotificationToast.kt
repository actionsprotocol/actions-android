package app.actionsfun.common.ui.components.toast

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import app.actionsfun.common.ui.AppPreview
import app.actionsfun.common.ui.R

@Composable
fun ErrorNotificationToast(
    message: String,
) {
    InAppNotificationToast(
        message = message,
        color = Color.Red,
        icon = painterResource(R.drawable.ds_close)
    )
}

@Composable
@AppPreview
private fun Preview() {
    ErrorNotificationToast(
        message = "Could not connect your wallet. Try again later."
    )
}