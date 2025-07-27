package app.actionsfun.feature.onboarding.presentation.model

import app.actionsfun.repository.actions.interactor.model.Market

internal sealed interface OnboardingEvent {

    data object OnboardingCompleted : OnboardingEvent

    data class MarketsLoaded(
        val markets: List<Market>,
    ) : OnboardingEvent
}

internal sealed interface OnboardingUIEvent : OnboardingEvent {

    data class ButtonClick(
        val button: OnboardingButton
    ) : OnboardingUIEvent
}