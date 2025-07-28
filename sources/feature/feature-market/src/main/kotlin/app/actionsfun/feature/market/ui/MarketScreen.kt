package app.actionsfun.feature.market.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import app.actionsfun.feature.market.presentation.MarketStore
import app.actionsfun.feature.market.presentation.MarketStoreProvider
import app.actionsfun.feature.market.presentation.model.MarketEffect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun MarketScreen(
    marketAddress: String,
    storeProvider: MarketStoreProvider,
) {
    val store = viewModel<MarketStore>(
        factory = storeProvider.viewModelFactory(marketAddress)
    )
    val state by store.state.collectAsState()

    fun handleEffect(effect: MarketEffect) {
    }

    LaunchedEffect(Unit) {
        store.effects
            .onEach(::handleEffect)
            .launchIn(this)
    }

    MarketScreenContent(
        state = state,
    )
}