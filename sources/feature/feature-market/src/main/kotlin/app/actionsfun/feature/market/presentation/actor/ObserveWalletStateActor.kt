package app.actionsfun.feature.market.presentation.actor

import app.actionsfun.common.arch.tea.component.Actor
import app.actionsfun.feature.market.presentation.model.MarketCommand.ObserveNetwork
import app.actionsfun.feature.market.presentation.model.MarketEvent.WalletStateChanged
import app.actionsfun.repository.solana.WalletRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import app.actionsfun.feature.market.presentation.model.MarketCommand as Command
import app.actionsfun.feature.market.presentation.model.MarketEvent as Event

internal class ObserveWalletStateActor(
    private val walletRepository: WalletRepository,
) : Actor<Command, Event> {

    override fun act(commands: Flow<Command>): Flow<Event> {
        return commands.filterIsInstance<ObserveNetwork>()
            .flatMapLatest {
                walletRepository.wallet
                    .map { wallet ->
                        val balance = walletRepository.getBalance()
                        WalletStateChanged(
                            publicKey = wallet.publicKey.takeIf { it.isNotEmpty() },
                            balance = balance?.toFloat() ?: 0f,
                        )
                    }
            }
    }
} 