package app.actionsfun.common.ui.browser

import android.content.Context
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri

fun Context.openUrlInCustomTab(url: String) {
    try {
        val customTabsIntent = CustomTabsIntent.Builder().build()
        customTabsIntent.launchUrl(this, url.toUri())
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
