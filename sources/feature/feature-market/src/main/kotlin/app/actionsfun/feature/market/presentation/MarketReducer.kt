package app.actionsfun.feature.market.presentation

import app.actionsfun.common.arch.tea.dsl.DslReducer
import app.actionsfun.feature.market.presentation.model.MarketCommand as Command
import app.actionsfun.feature.market.presentation.model.MarketEffect as Effect
import app.actionsfun.feature.market.presentation.model.MarketEvent as Event
import app.actionsfun.feature.market.presentation.model.MarketState as State
import app.actionsfun.feature.market.presentation.model.MarketUIEvent as UIEvent

internal class MarketReducer : DslReducer<Command, Effect, Event, State>() {

    override fun reduce(event: Event) {
        when (event) {
            is UIEvent -> reduceUI(event)
            else -> reduceEvent(event)
        }
    }

    private fun reduceUI(event: UIEvent) {
        when (event) {
            // Handle UI events here
            else -> Unit
        }
    }

    private fun reduceEvent(event: Event) {
        when (event) {
            // Handle domain events here
            else -> Unit
        }
    }
} 