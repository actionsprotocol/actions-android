package app.actionsfun.repository.onboarding

interface OnboardingRepository {

    suspend fun isOnboarded(): Boolean

    suspend fun setIsOnboarded(isOnboarded: Boolean)
}
