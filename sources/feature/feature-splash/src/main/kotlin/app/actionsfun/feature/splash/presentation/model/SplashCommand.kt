package app.actionsfun.feature.splash.presentation.model

internal sealed interface SplashCommand {

    data object LoadOnboardingState : SplashCommand
}