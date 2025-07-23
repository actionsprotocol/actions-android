package app.actionsfun.feature.splash.presentation

import app.actionsfun.common.arch.tea.dsl.DslReducer
import app.actionsfun.feature.splash.presentation.model.SplashCommand as Command
import app.actionsfun.feature.splash.presentation.model.SplashEffect as Effect
import app.actionsfun.feature.splash.presentation.model.SplashEvent as Event
import app.actionsfun.feature.splash.presentation.model.SplashState as State
import app.actionsfun.feature.splash.presentation.model.SplashUIEvent as UIEvent

internal class SplashReducer : DslReducer<Command, Effect, Event, State>() {

    override fun reduce(event: Event) {
        when (event) {
            is UIEvent -> reduceUI(event)
            else -> reduceEvent(event)
        }
    }

    private fun reduceUI(event: UIEvent) {
        when (event) {
            is UIEvent.LoadOnboardingState -> commands(Command.LoadOnboardingState)
        }
    }

    private fun reduceEvent(event: Event) {
        when (event) {
            is Event.OnboardingStateLoaded -> {
                if (event.isOnboarded) {
                    effects(Effect.OpenHome)
                } else {
                    effects(Effect.OpenOnboarding)
                }
            }
            else -> Unit
        }
    }
}