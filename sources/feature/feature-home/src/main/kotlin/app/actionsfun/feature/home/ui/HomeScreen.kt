package app.actionsfun.feature.home.ui

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import app.actionsfun.common.navigation.AppDestination
import app.actionsfun.common.navigation.core.Navigator
import app.actionsfun.feature.home.presentation.HomeStore
import app.actionsfun.feature.home.presentation.HomeStoreProvider
import app.actionsfun.feature.home.presentation.model.HomeEffect
import app.actionsfun.feature.home.presentation.model.HomeUIEvent.ConnectWalletClick
import app.actionsfun.feature.home.presentation.model.HomeUIEvent.PageChanged
import app.actionsfun.feature.home.presentation.model.HomeUIEvent.ProfileClick
import app.actionsfun.feature.home.presentation.model.HomeUIEvent.RetryLoadingClick
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun HomeScreen(
    navigator: Navigator,
    storeProvider: HomeStoreProvider,
) {
    val store = viewModel<HomeStore>(factory = storeProvider.viewModelFactory())
    val state by store.state.collectAsState()

    fun handleEffect(effect: HomeEffect) {
        when (effect) {
            is HomeEffect.OpenProfile -> {
                navigator.open(AppDestination.Profile)
            }
            is HomeEffect.ShowErrorToast -> {
                navigator.open(AppDestination.ErrorToast(effect.text))
            }
        }
    }

    BackHandler(onBack = navigator::back)

    LaunchedEffect(Unit) {
        store.effects
            .onEach(::handleEffect)
            .launchIn(this)
    }

    HomeScreenContent(
        state = state,
        navigator = navigator,
        connectWalletClick = { store.accept(ConnectWalletClick) },
        profileClick = { store.accept(ProfileClick) },
        retryLoadingClick = { store.accept(RetryLoadingClick) },
        onPageChanged = { store.accept(PageChanged(it)) }
    )
}
