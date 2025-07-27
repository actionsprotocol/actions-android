package app.actionsfun.feature.onboarding.presentation.model

internal sealed interface OnboardingEffect {

    data object OpenHome : OnboardingEffect
}