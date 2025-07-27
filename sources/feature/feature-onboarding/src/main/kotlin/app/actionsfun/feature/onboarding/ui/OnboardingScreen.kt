package app.actionsfun.feature.onboarding.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import app.actionsfun.common.navigation.AppDestination
import app.actionsfun.common.navigation.core.NavigationOption
import app.actionsfun.common.navigation.core.Navigator
import app.actionsfun.feature.onboarding.presentation.OnboardingStore
import app.actionsfun.feature.onboarding.presentation.OnboardingStoreProvider
import app.actionsfun.feature.onboarding.presentation.model.OnboardingEffect
import app.actionsfun.feature.onboarding.presentation.model.OnboardingUIEvent.ButtonClick
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun OnboardingScreen(
    navigator: Navigator,
    storeProvider: OnboardingStoreProvider,
) {
    val store = viewModel<OnboardingStore>(factory = storeProvider.viewModelFactory())
    val state by store.state.collectAsState()

    BackHandler(onBack = navigator::back)

    fun handleEffect(effect: OnboardingEffect) {
        when (effect) {
            is OnboardingEffect.OpenHome -> {
                navigator.open(AppDestination.Home) {
                    +NavigationOption.ClearStack
                    +NavigationOption.SingleTop
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        store.effects
            .onEach(::handleEffect)
            .launchIn(this)
    }

    OnboardingScreenContent(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        state = state,
        onClick = { store.accept(ButtonClick(it)) },
    )
}
