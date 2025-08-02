package app.actionsfun.feature.market.presentation.actor

import app.actionsfun.common.arch.tea.component.Actor
import app.actionsfun.feature.market.presentation.model.MarketCommand.LoadMarket
import app.actionsfun.feature.market.presentation.model.MarketEvent.BalanceLoaded
import app.actionsfun.repository.solana.WalletRepository
import app.actionsfun.repository.solana.internal.core.Convert
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest
import java.math.BigDecimal
import java.math.BigInteger
import app.actionsfun.feature.market.presentation.model.MarketCommand as Command
import app.actionsfun.feature.market.presentation.model.MarketEvent as Event

internal class GetBalanceActor(
    private val walletRepository: WalletRepository,
) : Actor<Command, Event> {

    override fun act(commands: Flow<Command>): Flow<Event> {
        return commands.filterIsInstance<LoadMarket>()
            .mapLatest {
                runCatching {
                    val balance = walletRepository.getBalance() ?: BigInteger.ZERO
                    val solBalance = Convert.lamportToSol(balance.toBigDecimal())
                    BalanceLoaded(solBalance)
                }.getOrElse {
                    BalanceLoaded(BigDecimal.ZERO)
                }
            }
    }
}
