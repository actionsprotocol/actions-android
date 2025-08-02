package app.actionsfun.feature.market.presentation.actor

import app.actionsfun.common.arch.tea.component.Actor
import app.actionsfun.feature.market.presentation.model.MarketCommand.ClaimSol
import app.actionsfun.feature.market.presentation.model.MarketEvent.SolClaimFailed
import app.actionsfun.feature.market.presentation.model.MarketEvent.SolClaimed
import app.actionsfun.repository.actions.interactor.ClaimSolInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest
import app.actionsfun.feature.market.presentation.model.MarketCommand as Command
import app.actionsfun.feature.market.presentation.model.MarketEvent as Event

internal class ClaimSolActor(
    private val claimSolInteractor: ClaimSolInteractor,
) : Actor<Command, Event> {

    override fun act(commands: Flow<Command>): Flow<Event> {
        return commands.filterIsInstance<ClaimSol>()
            .mapLatest { command ->
                runCatching {
                    val signature = claimSolInteractor.claim(
                        marketAddress = command.marketAddress,
                    )
                    SolClaimed(signature)

                }.getOrElse(::SolClaimFailed)
            }
    }
}
