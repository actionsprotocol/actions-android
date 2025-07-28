package app.actionsfun.feature.home.presentation.actor.wallet

import app.actionsfun.common.arch.tea.component.Actor
import app.actionsfun.feature.home.presentation.model.HomeCommand.ObserveWallet
import app.actionsfun.feature.home.presentation.model.HomeEvent.ConnectedWalletChanged
import app.actionsfun.repository.solana.WalletRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import app.actionsfun.feature.home.presentation.model.HomeCommand as Command
import app.actionsfun.feature.home.presentation.model.HomeEvent as Event

internal class ObserveWalletActor(
    private val walletRepository: WalletRepository,
) : Actor<Command, Event> {

    override fun act(commands: Flow<Command>): Flow<Event> {
        return commands.filterIsInstance<ObserveWallet>()
            .flatMapLatest { walletRepository.wallet }
            .mapLatest { wallet ->
                ConnectedWalletChanged(
                    userId = wallet.userId,
                    publicKey = wallet.publicKey,
                    authToken = wallet.authToken,
                )
            }
    }
}