package app.actionsfun.feature.profile.presentation.actor

import app.actionsfun.common.arch.tea.component.Actor
import app.actionsfun.feature.profile.presentation.model.ProfileCommand.ConnectWallet
import app.actionsfun.feature.profile.presentation.model.ProfileEvent
import app.actionsfun.repository.solana.WalletRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest
import app.actionsfun.feature.profile.presentation.model.ProfileCommand as Command
import app.actionsfun.feature.profile.presentation.model.ProfileEvent as Event

internal class ConnectWalletActor(
    private val walletRepository: WalletRepository,
) : Actor<Command, Event> {

    override fun act(commands: Flow<Command>): Flow<Event> {
        return commands.filterIsInstance<ConnectWallet>()
            .mapLatest {
                runCatching { walletRepository.connectWallet() }
                    .mapCatching { Event.EmptyEvent }
                    .getOrElse(ProfileEvent::ConnectWalletFailed)
            }
    }
}
