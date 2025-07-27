package app.actionsfun.feature.onboarding.presentation.model

internal sealed interface OnboardingCommand {

    data object CompleteOnboarding : OnboardingCommand

    data object GetMarkets : OnboardingCommand
}