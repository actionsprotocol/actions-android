package app.actionsfun.feature.splash.di

import app.actionsfun.feature.splash.presentation.SplashReducer
import app.actionsfun.feature.splash.presentation.SplashStore
import app.actionsfun.feature.splash.presentation.SplashStoreProvider
import app.actionsfun.feature.splash.presentation.actor.LoadOnboardingStateActor
import app.actionsfun.feature.splash.presentation.model.SplashState
import org.koin.dsl.module

val SplashModule = module {
    factory<SplashStoreProvider> {
        object : SplashStoreProvider() {
            override fun provide(): SplashStore {
                return SplashStore(
                    initialEvents = listOf(),
                    initialState = SplashState,
                    actors = setOf(
                        LoadOnboardingStateActor(onboardingRepository = get()),
                    ),
                    reducer = SplashReducer(),
                )
            }
        }
    }
}