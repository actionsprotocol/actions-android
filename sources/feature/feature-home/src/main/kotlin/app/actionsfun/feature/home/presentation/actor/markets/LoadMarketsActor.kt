package app.actionsfun.feature.home.presentation.actor.markets

import app.actionsfun.common.arch.tea.component.Actor
import app.actionsfun.feature.home.presentation.model.HomeCommand.LoadMarkets
import app.actionsfun.feature.home.presentation.model.HomeEvent.MarketsLoaded
import app.actionsfun.feature.home.presentation.model.HomeEvent.MarketsLoadingError
import app.actionsfun.repository.actions.interactor.GetMarketsInteractor
import app.actionsfun.repository.actions.interactor.model.Market
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest
import app.actionsfun.feature.home.presentation.model.HomeCommand as Command
import app.actionsfun.feature.home.presentation.model.HomeEvent as Event

internal class LoadMarketsActor(
    private val getMarketsInteractor: GetMarketsInteractor,
) : Actor<Command, Event> {

    override fun act(commands: Flow<Command>): Flow<Event> {
        return commands.filterIsInstance<LoadMarkets>()
            .mapLatest {
                runCatching {
                    val markets = getMarketsInteractor.get()
                        .sortedByDescending(Market::createdAt)
//                        .filter { market ->
//                            market.creatorTwitterUsername.isNotEmpty()
//                        }
                    MarketsLoaded(markets)
                }
                .getOrElse(::MarketsLoadingError)
            }
    }
}