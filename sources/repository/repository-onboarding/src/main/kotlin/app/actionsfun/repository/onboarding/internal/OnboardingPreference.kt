package app.actionsfun.repository.onboarding.internal

import app.actionsfun.repository.preferences.Preference

internal data object OnboardingPreference : Preference<Boolean> {
    override val key: String = "Key:IsOnboarded"
    override val defaultValue = false
}
