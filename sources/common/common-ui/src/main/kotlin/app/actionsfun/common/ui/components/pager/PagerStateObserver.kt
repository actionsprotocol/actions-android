package app.actionsfun.common.ui.components.pager

import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

typealias PageChangeCallback = (from: Int?, to: Int) -> Unit

@Composable
fun PagerStateObserver(
    pagerState: PagerState,
    enabled: Boolean = true,
    pageKey: (Int) -> Any = { it },
    onChange: PageChangeCallback,
) {
    if (enabled) {
        with(pagerState) {
            ObservePageChange(onChange, pageKey)
        }
    }
}

@Composable
private fun PagerState.ObservePageChange(
    callback: PageChangeCallback,
    pageKey: (Int) -> Any = { it },
) {
    val currentPage = currentPage
    val previousPage = remember { mutableStateOf<Int?>(null) }
    val previousPageKey = remember { mutableStateOf<Any?>(null) }
    val currentPageKey = pageKey(currentPage)

    LaunchedEffect(currentPage, currentPageKey) {
        callback(previousPage.value, currentPage)

        previousPage.value = currentPage
        previousPageKey.value = currentPageKey
    }
}