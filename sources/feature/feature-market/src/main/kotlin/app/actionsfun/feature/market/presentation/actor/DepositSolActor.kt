package app.actionsfun.feature.market.presentation.actor

import app.actionsfun.common.arch.tea.component.Actor
import app.actionsfun.feature.market.presentation.model.MarketCommand.DepositSol
import app.actionsfun.feature.market.presentation.model.MarketEvent.SolDepositFailed
import app.actionsfun.feature.market.presentation.model.MarketEvent.SolDeposited
import app.actionsfun.repository.actions.interactor.DepositToMarketInteractor
import app.actionsfun.repository.solana.internal.core.Convert
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest
import app.actionsfun.feature.market.presentation.model.MarketCommand as Command
import app.actionsfun.feature.market.presentation.model.MarketEvent as Event

internal class DepositSolActor(
    private val depositToMarketInteractor: DepositToMarketInteractor,
) : Actor<Command, Event> {

    override fun act(commands: Flow<Command>): Flow<Event> {
        return commands.filterIsInstance<DepositSol>()
            .mapLatest { command ->
                runCatching {
                    val signature = depositToMarketInteractor.deposit(
                        marketAddress = command.marketAddress,
                        amount = Convert.solToLamport(command.amountSol.toString()),
                        option = command.option,
                    )
                    SolDeposited(signature)

                }.getOrElse(::SolDepositFailed)
            }
    }
}
