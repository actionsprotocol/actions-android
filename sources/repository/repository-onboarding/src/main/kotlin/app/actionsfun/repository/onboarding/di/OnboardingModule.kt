package app.actionsfun.repository.onboarding.di

import app.actionsfun.repository.onboarding.OnboardingRepository
import app.actionsfun.repository.onboarding.internal.PreferencesOnboardingRepository
import org.koin.dsl.module

val OnboardingRepositoryModule = module {
    single<OnboardingRepository> {
        PreferencesOnboardingRepository(get())
    }
}