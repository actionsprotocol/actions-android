package app.actionsfun.feature.splash.ui

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import app.actionsfun.common.navigation.AppDestination
import app.actionsfun.common.navigation.core.NavigationOption
import app.actionsfun.common.navigation.core.Navigator
import app.actionsfun.feature.splash.presentation.SplashStore
import app.actionsfun.feature.splash.presentation.SplashStoreProvider
import app.actionsfun.feature.splash.presentation.model.SplashEffect
import app.actionsfun.feature.splash.presentation.model.SplashUIEvent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun SplashScreen(
    navigator: Navigator,
    storeProvider: SplashStoreProvider,
) {
    val store = viewModel<SplashStore>(factory = storeProvider.viewModelFactory())

    BackHandler(onBack = navigator::back)

    fun handleEffect(effect: SplashEffect) {
        when (effect) {
            is SplashEffect.OpenHome -> {
                navigator.open(AppDestination.Home) {
                    +NavigationOption.ClearStack
                    +NavigationOption.SingleTop
                }
            }
            is SplashEffect.OpenOnboarding -> {
                navigator.open(AppDestination.Onboarding) {
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

    SplashScreenContent {
        store.accept(SplashUIEvent.LoadOnboardingState)
    }
}