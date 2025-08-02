package app.actionsfun.feature.profile.presentation.actor

import app.actionsfun.common.arch.tea.component.Actor
import app.actionsfun.feature.profile.presentation.model.ProfileCommand
import app.actionsfun.feature.profile.presentation.model.ProfileEvent
import app.actionsfun.repository.solana.WalletRepository
import app.actionsfun.repository.user.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest

internal class DisconnectWalletActor(
    private val walletRepository: WalletRepository,
    private val userRepository: UserRepository,
) : Actor<ProfileCommand, ProfileEvent> {

    override fun act(commands: Flow<ProfileCommand>): Flow<ProfileEvent> {
        return commands.filterIsInstance<ProfileCommand.DisconnectWallet>()
            .mapLatest {
                walletRepository.disconnectWallet()
                userRepository.logOut()
                ProfileEvent.WalletDisconnected
            }
    }
}