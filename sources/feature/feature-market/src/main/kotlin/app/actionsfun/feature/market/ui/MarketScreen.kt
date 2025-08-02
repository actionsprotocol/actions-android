package app.actionsfun.feature.market.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import app.actionsfun.common.navigation.AppDestination
import app.actionsfun.common.navigation.core.Navigator
import app.actionsfun.feature.market.presentation.MarketStore
import app.actionsfun.feature.market.presentation.MarketStoreProvider
import app.actionsfun.feature.market.presentation.model.MarketEffect
import app.actionsfun.feature.market.presentation.model.MarketUIEvent
import app.actionsfun.feature.market.presentation.model.MarketUIEvent.DepositActionButtonClicked
import app.actionsfun.feature.market.presentation.model.MarketUIEvent.DepositAmountChanged
import app.actionsfun.feature.market.presentation.model.MarketUIEvent.DepositOptionChanged
import app.actionsfun.feature.market.presentation.model.MarketUIEvent.DepositQuickAmountClicked
import app.actionsfun.feature.market.presentation.model.MarketUIEvent.SendReply
import app.actionsfun.feature.market.presentation.model.MarketUIEvent.ConnectWallet
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun MarketScreen(
    marketAddress: String,
    navigator: Navigator,
    storeProvider: MarketStoreProvider,
) {
    val store = viewModel<MarketStore>(
        factory = storeProvider.viewModelFactory(marketAddress),
        key = marketAddress,
    )
    val state by store.state.collectAsState()

    fun handleEffect(effect: MarketEffect) {
        when (effect) {
            is MarketEffect.ShowErrorToast -> {
                navigator.open(AppDestination.ErrorToast(effect.message))
            }
            is MarketEffect.ShowSuccessToast -> {
                navigator.open(AppDestination.SuccessToast(effect.message))
            }
        }
    }

    LaunchedEffect(Unit) {
        store.effects
            .onEach(::handleEffect)
            .launchIn(this)
    }

    MarketScreenContent(
        state = state,
        depositOptionClick = { store.accept(DepositOptionChanged(it)) },
        depositQuickAmountClick = { store.accept(DepositQuickAmountClicked(it)) },
        depositValueChange = { store.accept(DepositAmountChanged(it)) },
        depositActionButtonClick = { store.accept(DepositActionButtonClicked) },
        onSendReply = { store.accept(SendReply(it)) },
        onConnectWallet = { store.accept(ConnectWallet) },
    )
}