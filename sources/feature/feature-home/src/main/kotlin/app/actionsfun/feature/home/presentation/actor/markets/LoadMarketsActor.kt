package app.actionsfun.feature.home.presentation.actor.markets

import app.actionsfun.common.arch.tea.component.Actor
import app.actionsfun.feature.home.presentation.model.HomeCommand.LoadMarkets
import app.actionsfun.feature.home.presentation.model.HomeEvent.MarketsLoaded
import app.actionsfun.feature.home.presentation.model.HomeEvent.MarketsLoadingError
import app.actionsfun.repository.actions.interactor.GetMarketsInteractor
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest
import kotlin.time.Duration.Companion.seconds
import app.actionsfun.feature.home.presentation.model.HomeCommand as Command
import app.actionsfun.feature.home.presentation.model.HomeEvent as Event

internal class LoadMarketsActor(
    private val getMarketsInteractor: GetMarketsInteractor,
) : Actor<Command, Event> {

    override fun act(commands: Flow<Command>): Flow<Event> {
        return commands.filterIsInstance<LoadMarkets>()
            .mapLatest {
                runCatching {
                    delay(1.seconds)
                    val markets = getMarketsInteractor.get()
                    MarketsLoaded(markets)
                }
                .getOrElse(::MarketsLoadingError)
            }
    }
}