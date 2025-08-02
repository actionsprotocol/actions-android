package app.actionsfun.feature.profile.presentation.actor

import app.actionsfun.common.arch.tea.component.Actor
import app.actionsfun.feature.profile.presentation.model.ProfileCommand.ObserveWallet
import app.actionsfun.feature.profile.presentation.model.ProfileEvent.ConnectedWalletChanged
import app.actionsfun.repository.solana.ConnectedWallet
import app.actionsfun.repository.solana.WalletRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import app.actionsfun.feature.profile.presentation.model.ProfileCommand as Command
import app.actionsfun.feature.profile.presentation.model.ProfileEvent as Event

internal class ObserveWalletActor(
    private val walletRepository: WalletRepository,
) : Actor<Command, Event> {

    override fun act(commands: Flow<Command>): Flow<Event> {
        return commands.filterIsInstance<ObserveWallet>()
            .flatMapLatest {
                walletRepository.wallet
                    .distinctUntilChangedBy(ConnectedWallet::publicKey)
                    .mapLatest { wallet ->
                        ConnectedWalletChanged(
                            userId = wallet.userId,
                            publicKey = wallet.publicKey
                        )
                    }
            }
    }
}
