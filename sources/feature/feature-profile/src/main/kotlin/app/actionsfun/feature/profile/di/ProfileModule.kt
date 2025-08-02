package app.actionsfun.feature.profile.di

import app.actionsfun.feature.profile.domain.GetUserParticipatedMarketsWithClaimStatus
import app.actionsfun.feature.profile.presentation.ProfileReducer
import app.actionsfun.feature.profile.presentation.ProfileStore
import app.actionsfun.feature.profile.presentation.ProfileStoreProvider
import app.actionsfun.feature.profile.presentation.actor.ClaimWinningsActor
import app.actionsfun.feature.profile.presentation.actor.ConnectWalletActor
import app.actionsfun.feature.profile.presentation.actor.DisconnectWalletActor
import app.actionsfun.feature.profile.presentation.actor.LoadMarketsActor
import app.actionsfun.feature.profile.presentation.actor.ObserveMarketsActor
import app.actionsfun.feature.profile.presentation.actor.ObserveWalletActor
import app.actionsfun.feature.profile.presentation.model.ProfileEvent.Initialize
import app.actionsfun.feature.profile.presentation.model.ProfileState
import app.actionsfun.feature.profile.ui.ProfileUIStateMapper
import org.koin.dsl.module

val ProfileModule = module {
    factory<ProfileStoreProvider> {
        object : ProfileStoreProvider() {
            override fun provide(): ProfileStore {
                return ProfileStore(
                    initialEvents = listOf(Initialize),
                    initialState = ProfileState.Default,
                    uiStateMapper = ProfileUIStateMapper(),
                    reducer = ProfileReducer(),
                    actors = setOf(
                        ObserveMarketsActor(
                            walletRepository = get(),
                            getParticipatedMarkets = GetUserParticipatedMarketsWithClaimStatus(get(), get()),
                        ),
                        LoadMarketsActor(
                            walletRepository = get(),
                            getParticipatedMarkets = GetUserParticipatedMarketsWithClaimStatus(get(), get()),
                        ),
                        ClaimWinningsActor(claimSolInteractor = get()),
                        ObserveWalletActor(walletRepository = get()),
                        ConnectWalletActor(walletRepository = get()),
                        DisconnectWalletActor(
                            walletRepository = get(),
                            userRepository = get(),
                        ),
                    ),
                )
            }
        }
    }
}