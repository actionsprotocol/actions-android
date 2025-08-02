package app.actionsfun.feature.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.actionsfun.common.arch.tea.TeaViewModel
import app.actionsfun.common.arch.tea.component.Actor
import app.actionsfun.common.arch.tea.component.Reducer
import app.actionsfun.common.arch.tea.component.UiStateMapper
import app.actionsfun.feature.profile.presentation.model.ProfileCommand as Command
import app.actionsfun.feature.profile.presentation.model.ProfileEffect as Effect
import app.actionsfun.feature.profile.presentation.model.ProfileEvent as Event
import app.actionsfun.feature.profile.presentation.model.ProfileState as State
import app.actionsfun.feature.profile.ui.ProfileUIState as UIState
import app.actionsfun.feature.profile.presentation.model.ProfileUIEvent as UIEvent

abstract class ProfileStoreProvider {

    internal abstract fun provide(): ProfileStore

    fun viewModelFactory(): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return provide() as T
            }
        }
    }
}

internal class ProfileStore(
    initialState: State,
    actors: Set<Actor<Command, Event>>,
    reducer: Reducer<Command, Effect, Event, State>,
    uiStateMapper: UiStateMapper<State, UIState>,
    initialEvents: List<Event> = emptyList(),
) : TeaViewModel<Command, Effect, Event, UIEvent, State, UIState>(
    initialState = initialState,
    actors = actors,
    reducer = reducer,
    uiStateMapper = uiStateMapper,
    initialEvents = initialEvents,
)