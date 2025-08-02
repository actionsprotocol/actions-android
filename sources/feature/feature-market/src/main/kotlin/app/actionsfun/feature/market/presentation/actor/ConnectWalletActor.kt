package app.actionsfun.feature.market.presentation.actor

import app.actionsfun.common.arch.tea.component.Actor
import app.actionsfun.feature.market.presentation.model.MarketCommand.ConnectWallet
import app.actionsfun.feature.market.presentation.model.MarketEvent.WalletConnected
import app.actionsfun.feature.market.presentation.model.MarketEvent.WalletConnectionFailed
import app.actionsfun.repository.solana.WalletRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest
import app.actionsfun.feature.market.presentation.model.MarketCommand as Command
import app.actionsfun.feature.market.presentation.model.MarketEvent as Event

internal class ConnectWalletActor(
    private val walletRepository: WalletRepository,
) : Actor<Command, Event> {

    override fun act(commands: Flow<Command>): Flow<Event> {
        return commands.filterIsInstance<ConnectWallet>()
            .mapLatest {
                runCatching {
                    walletRepository.connectWallet()
                    WalletConnected
                }
                .getOrElse(::WalletConnectionFailed)
            }
    }
} 