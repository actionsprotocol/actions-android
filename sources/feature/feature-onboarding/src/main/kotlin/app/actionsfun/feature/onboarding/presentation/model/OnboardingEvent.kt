package app.actionsfun.feature.onboarding.presentation.model

internal sealed interface OnboardingEvent {

    data object OnboardingCompleted : OnboardingEvent
}

internal sealed interface OnboardingUIEvent : OnboardingEvent {

    data class ButtonClick(
        val button: OnboardingButton
    ) : OnboardingUIEvent
}