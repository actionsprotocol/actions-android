package app.actionsfun.feature.onboarding.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.actionsfun.common.arch.tea.TeaViewModel
import app.actionsfun.common.arch.tea.component.Actor
import app.actionsfun.common.arch.tea.component.Reducer
import app.actionsfun.feature.onboarding.presentation.model.OnboardingCommand as Command
import app.actionsfun.feature.onboarding.presentation.model.OnboardingEffect as Effect
import app.actionsfun.feature.onboarding.presentation.model.OnboardingEvent as Event
import app.actionsfun.feature.onboarding.presentation.model.OnboardingState as State
import app.actionsfun.feature.onboarding.presentation.model.OnboardingUIEvent as UIEvent

abstract class OnboardingStoreProvider {

    internal abstract fun provide(): OnboardingStore

    fun viewModelFactory(): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return provide() as T
            }
        }
    }
}

internal class OnboardingStore(
    initialState: State,
    actors: Set<Actor<Command, Event>>,
    reducer: Reducer<Command, Effect, Event, State>,
    initialEvents: List<Event> = emptyList(),
) : TeaViewModel<Command, Effect, Event, UIEvent, State, State>(
    initialState = initialState,
    actors = actors,
    reducer = reducer,
    initialEvents = initialEvents,
)
