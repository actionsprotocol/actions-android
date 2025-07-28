package app.actionsfun.feature.home.presentation.actor.wallet

import app.actionsfun.common.arch.tea.component.Actor
import app.actionsfun.feature.home.presentation.model.HomeCommand.ConnectWallet
import app.actionsfun.repository.solana.WalletRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import app.actionsfun.feature.home.presentation.model.HomeCommand as Command
import app.actionsfun.feature.home.presentation.model.HomeEvent as Event

internal class ConnectWalletActor(
    private val walletRepository: WalletRepository,
) : Actor<Command, Event> {

    override fun act(commands: Flow<Command>): Flow<Event> {
        return commands.filterIsInstance<ConnectWallet>()
            .onEach { walletRepository.connectWallet() }
            .mapLatest { Event.EmptyEvent }
    }
}