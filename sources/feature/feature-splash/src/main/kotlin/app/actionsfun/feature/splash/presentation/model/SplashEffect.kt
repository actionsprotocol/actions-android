package app.actionsfun.feature.splash.presentation.model

internal sealed interface SplashEffect {

    data object OpenOnboarding : SplashEffect

    data object OpenHome : SplashEffect
}