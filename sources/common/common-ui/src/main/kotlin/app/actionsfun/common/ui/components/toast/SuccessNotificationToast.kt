package app.actionsfun.common.ui.components.toast

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import app.actionsfun.common.ui.AppPreview
import app.actionsfun.common.ui.R

@Composable
fun SuccessNotificationToast(
    message: String,
) {
    InAppNotificationToast(
        message = message,
        color = Color.Green,
        icon = painterResource(R.drawable.ds_check)
    )
}

@Composable
@AppPreview
private fun Preview() {
    SuccessNotificationToast(
        message = "Wallet successfully connected."
    )
}