package app.actionsfun.feature.profile.ui

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import app.actionsfun.common.navigation.AppDestination
import app.actionsfun.common.navigation.core.Navigator
import app.actionsfun.common.ui.browser.openUrlInCustomTab
import app.actionsfun.feature.profile.presentation.ProfileStore
import app.actionsfun.feature.profile.presentation.ProfileStoreProvider
import app.actionsfun.feature.profile.presentation.model.ProfileEffect
import app.actionsfun.feature.profile.presentation.model.ProfileUIEvent.ClaimClicked
import app.actionsfun.feature.profile.presentation.model.ProfileUIEvent.ConnectWalletClick
import app.actionsfun.feature.profile.presentation.model.ProfileUIEvent.RetryLoadMarketsClick
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun ProfileScreen(
    navigator: Navigator,
    storeProvider: ProfileStoreProvider,
) {
    val context = LocalContext.current
    val store = viewModel<ProfileStore>(factory = storeProvider.viewModelFactory())
    val state by store.state.collectAsState()

    BackHandler(onBack = navigator::back)

    fun openUrl(url: String) {
        context.openUrlInCustomTab(url)
    }

    fun handleEffect(effect: ProfileEffect) {
        when (effect) {
            is ProfileEffect.ShowErrorToast -> {
                navigator.open(AppDestination.ErrorToast(effect.message))
            }
            is ProfileEffect.ShowSuccessToast -> {
                navigator.open(AppDestination.SuccessToast(effect.message))
            }
        }
    }

    LaunchedEffect(Unit) {
        store.effects
            .onEach(::handleEffect)
            .launchIn(this)
    }

    ProfileScreenContent(
        state = state,
        backClick = navigator::back,
        urlClick = ::openUrl,
        connectWalletClick = { store.accept(ConnectWalletClick) },
        publicKeyClick = {}, // TODO
        retryClick = { store.accept(RetryLoadMarketsClick) },
        claimClick = { store.accept(ClaimClicked(it)) }
    )
}