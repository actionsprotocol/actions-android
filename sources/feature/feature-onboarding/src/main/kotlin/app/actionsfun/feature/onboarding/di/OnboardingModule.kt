package app.actionsfun.feature.onboarding.di

import app.actionsfun.feature.onboarding.presentation.OnboardingReducer
import app.actionsfun.feature.onboarding.presentation.OnboardingStore
import app.actionsfun.feature.onboarding.presentation.OnboardingStoreProvider
import app.actionsfun.feature.onboarding.presentation.actor.CompleteOnboardingActor
import app.actionsfun.feature.onboarding.presentation.actor.LoadMarketsActor
import app.actionsfun.feature.onboarding.presentation.model.OnboardingState
import org.koin.dsl.module

val OnboardingModule = module {
    factory<OnboardingStoreProvider> {
        object : OnboardingStoreProvider() {
            override fun provide(): OnboardingStore {
                return OnboardingStore(
                    initialEvents = listOf(),
                    initialState = OnboardingState.Default,
                    actors = setOf(
                        CompleteOnboardingActor(onboardingRepository = get()),
                        LoadMarketsActor(getMarketsInteractor = get()),
                    ),
                    reducer = OnboardingReducer(),
                )
            }
        }
    }
}