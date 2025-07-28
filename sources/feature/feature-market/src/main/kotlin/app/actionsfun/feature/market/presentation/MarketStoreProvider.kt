package app.actionsfun.feature.market.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.actionsfun.common.arch.tea.TeaViewModel
import app.actionsfun.common.arch.tea.component.Actor
import app.actionsfun.common.arch.tea.component.Reducer
import app.actionsfun.common.arch.tea.component.UiStateMapper
import app.actionsfun.feature.market.presentation.model.MarketCommand as Command
import app.actionsfun.feature.market.presentation.model.MarketEffect as Effect
import app.actionsfun.feature.market.presentation.model.MarketEvent as Event
import app.actionsfun.feature.market.presentation.model.MarketState as State
import app.actionsfun.feature.market.ui.MarketUIState as UIState
import app.actionsfun.feature.market.presentation.model.MarketUIEvent as UIEvent

abstract class MarketStoreProvider {

    internal abstract fun provide(marketAddress: String): MarketStore

    fun viewModelFactory(marketAddress: String): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return provide(marketAddress) as T
            }
        }
    }
}

internal class MarketStore(
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