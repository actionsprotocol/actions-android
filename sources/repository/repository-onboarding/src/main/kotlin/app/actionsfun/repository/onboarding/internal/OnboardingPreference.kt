package app.actionsfun.repository.onboarding.internal

import androidx.annotation.Keep
import app.actionsfun.repository.preferences.Preference

@Keep
internal data object OnboardingPreference : Preference<Boolean> {
    override val key: String = "Key:IsOnboarded"
    override val defaultValue = false
}
