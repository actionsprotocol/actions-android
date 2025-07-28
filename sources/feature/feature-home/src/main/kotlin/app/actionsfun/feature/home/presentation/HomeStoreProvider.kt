package app.actionsfun.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.actionsfun.common.arch.tea.TeaViewModel
import app.actionsfun.common.arch.tea.component.Actor
import app.actionsfun.common.arch.tea.component.Reducer
import app.actionsfun.common.arch.tea.component.UiStateMapper
import app.actionsfun.feature.home.presentation.model.HomeCommand as Command
import app.actionsfun.feature.home.presentation.model.HomeEffect as Effect
import app.actionsfun.feature.home.presentation.model.HomeEvent as Event
import app.actionsfun.feature.home.presentation.model.HomeState as State
import app.actionsfun.feature.home.ui.HomeUIState as UIState
import app.actionsfun.feature.home.presentation.model.HomeUIEvent as UIEvent

abstract class HomeStoreProvider {

    internal abstract fun provide(): HomeStore

    fun viewModelFactory(): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return provide() as T
            }
        }
    }
}

internal class HomeStore(
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
