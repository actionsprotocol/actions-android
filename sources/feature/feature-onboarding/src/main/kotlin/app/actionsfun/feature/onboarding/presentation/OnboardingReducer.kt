package app.actionsfun.feature.onboarding.presentation

import app.actionsfun.common.arch.tea.dsl.DslReducer
import app.actionsfun.feature.onboarding.presentation.model.OnboardingCommand
import timber.log.Timber
import app.actionsfun.feature.onboarding.presentation.model.OnboardingCommand as Command
import app.actionsfun.feature.onboarding.presentation.model.OnboardingEffect as Effect
import app.actionsfun.feature.onboarding.presentation.model.OnboardingEvent as Event
import app.actionsfun.feature.onboarding.presentation.model.OnboardingState as State
import app.actionsfun.feature.onboarding.presentation.model.OnboardingUIEvent as UIEvent

internal class OnboardingReducer : DslReducer<Command, Effect, Event, State>() {

    override fun reduce(event: Event) {
        when (event) {
            is UIEvent -> reduceUI(event)
            else -> reduceEvent(event)
        }
    }

    private fun reduceUI(event: UIEvent) {
        when (event) {
            is UIEvent.ButtonClick -> {
                commands(Command.GetMarkets)
                reduceButtonClick(event)
            }
        }
    }

    private fun reduceEvent(event: Event) {
        when (event) {
            is Event.OnboardingCompleted -> effects(Effect.OpenHome)
            is Event.MarketsLoaded -> {
                Timber.tag("Lounah").d("markets: ${event.markets[event.markets.lastIndex]}")
            }
            else -> Unit
        }
    }

    private fun reduceButtonClick(event: UIEvent.ButtonClick) {
        when (state.selectedScreen) {
            state.screens.lastIndex -> commands(OnboardingCommand.CompleteOnboarding)
            else -> state {
                copy(
                    selectedScreen = (state.selectedScreen + 1)
                        .coerceIn(0..state.screens.lastIndex)
                )
            }
        }
    }
}