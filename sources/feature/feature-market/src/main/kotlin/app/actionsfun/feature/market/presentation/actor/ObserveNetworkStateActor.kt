package app.actionsfun.feature.market.presentation.actor

import app.actionsfun.common.arch.tea.component.Actor
import app.actionsfun.common.network.connection.NetworkConnectionObserver
import app.actionsfun.common.network.connection.isConnected
import app.actionsfun.feature.market.presentation.model.MarketCommand.ObserveNetwork
import app.actionsfun.feature.market.presentation.model.MarketEvent.ConnectionStateChanged
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import app.actionsfun.feature.market.presentation.model.MarketCommand as Command
import app.actionsfun.feature.market.presentation.model.MarketEvent as Event

internal class ObserveNetworkStateActor(
    private val networkObserver: NetworkConnectionObserver,
) : Actor<Command, Event> {

    override fun act(commands: Flow<Command>): Flow<Event> {
        return commands.filterIsInstance<ObserveNetwork>()
            .flatMapLatest {
                networkObserver.connection
                    .distinctUntilChangedBy { connection -> connection.isConnected }
                    .mapLatest { connection ->
                        ConnectionStateChanged(connected = connection.isConnected)
                    }
            }
    }
}