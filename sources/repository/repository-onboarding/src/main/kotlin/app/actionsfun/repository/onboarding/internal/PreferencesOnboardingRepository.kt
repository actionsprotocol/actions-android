package app.actionsfun.repository.onboarding.internal

import app.actionsfun.repository.onboarding.OnboardingRepository
import app.actionsfun.repository.preferences.AppPreferences
import app.actionsfun.repository.preferences.get
import app.actionsfun.repository.preferences.set
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class PreferencesOnboardingRepository(
    private val appPreferences: AppPreferences,
) : OnboardingRepository {

    override suspend fun isOnboarded(): Boolean {
        return withContext(Dispatchers.Main) {
            (appPreferences.get<OnboardingPreference>() as? Boolean) ?: false
        }
    }

    override suspend fun setIsOnboarded(isOnboarded: Boolean) {
        withContext(Dispatchers.Main) {
            appPreferences.set<OnboardingPreference>(isOnboarded)
        }
    }
}