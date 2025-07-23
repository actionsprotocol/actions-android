package app.actionsfun.feature.splash.presentation.model

internal sealed interface SplashEvent {

    data class OnboardingStateLoaded(val isOnboarded: Boolean) : SplashEvent
}

internal sealed interface SplashUIEvent : SplashEvent {

    data object LoadOnboardingState : SplashUIEvent
}