package app.actionsfun.feature.market.presentation.actor

import app.actionsfun.common.arch.tea.component.Actor
import app.actionsfun.feature.market.domain.GetMarketWithClaimStatus
import app.actionsfun.feature.market.presentation.model.MarketCommand.LoadMarket
import app.actionsfun.feature.market.presentation.model.MarketEvent.MarketLoaded
import app.actionsfun.feature.market.presentation.model.MarketEvent.MarketLoadingError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest
import app.actionsfun.feature.market.presentation.model.MarketCommand as Command
import app.actionsfun.feature.market.presentation.model.MarketEvent as Event

internal class LoadMarketActor(
    private val getMarketInteractor: GetMarketWithClaimStatus,
) : Actor<Command, Event> {

    override fun act(commands: Flow<Command>): Flow<Event> {
        return commands.filterIsInstance<LoadMarket>()
            .mapLatest { command ->
                runCatching {
                    val market = getMarketInteractor.get(command.marketAddress)
                    MarketLoaded(market)
                }
                .getOrElse(::MarketLoadingError)
            }
    }
}
