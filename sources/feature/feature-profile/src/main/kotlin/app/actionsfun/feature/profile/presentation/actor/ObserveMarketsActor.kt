package app.actionsfun.feature.profile.presentation.actor

import app.actionsfun.common.arch.tea.component.Actor
import app.actionsfun.feature.profile.domain.GetUserParticipatedMarketsWithClaimStatus
import app.actionsfun.feature.profile.presentation.model.ProfileCommand.ObserveMarkets
import app.actionsfun.feature.profile.presentation.model.ProfileEvent.MarketsLoaded
import app.actionsfun.feature.profile.presentation.model.ProfileEvent.MarketsLoadingError
import app.actionsfun.repository.actions.interactor.GetMarketClaimStatusInteractor
import app.actionsfun.repository.actions.interactor.GetParticipatedMarketsInteractor
import app.actionsfun.repository.solana.ConnectedWallet
import app.actionsfun.repository.solana.WalletRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import app.actionsfun.feature.profile.presentation.model.ProfileCommand as Command
import app.actionsfun.feature.profile.presentation.model.ProfileEvent as Event

internal class ObserveMarketsActor(
    private val walletRepository: WalletRepository,
    private val getParticipatedMarkets: GetUserParticipatedMarketsWithClaimStatus,
) : Actor<Command, Event> {

    override fun act(commands: Flow<Command>): Flow<Event> {
        return commands.filterIsInstance<ObserveMarkets>()
            .flatMapLatest {
                walletRepository.wallet
                    .distinctUntilChangedBy(ConnectedWallet::publicKey)
                    .mapLatest { wallet ->
                        runCatching {
                            val markets = getParticipatedMarkets.get()
                            MarketsLoaded(
                                markets = markets,
                                publicKey = wallet.publicKey
                            )
                        }
                            .getOrElse(::MarketsLoadingError)
                    }
            }
    }
}
