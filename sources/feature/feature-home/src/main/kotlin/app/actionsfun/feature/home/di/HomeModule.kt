package app.actionsfun.feature.home.di

import app.actionsfun.feature.home.presentation.HomeReducer
import app.actionsfun.feature.home.presentation.HomeStore
import app.actionsfun.feature.home.presentation.HomeStoreProvider
import app.actionsfun.feature.home.presentation.actor.markets.LoadMarketsActor
import app.actionsfun.feature.home.presentation.actor.wallet.ConnectWalletActor
import app.actionsfun.feature.home.presentation.actor.wallet.ObserveWalletActor
import app.actionsfun.feature.home.presentation.model.HomeEvent
import app.actionsfun.feature.home.presentation.model.HomeState
import app.actionsfun.feature.home.ui.HomeUIStateMapper
import org.koin.dsl.module

val HomeModule = module {
    factory<HomeStoreProvider> {
        object : HomeStoreProvider() {
            override fun provide(): HomeStore {
                return HomeStore(
                    initialEvents = listOf(HomeEvent.Initialize),
                    initialState = HomeState.Default,
                    uiStateMapper = HomeUIStateMapper(),
                    reducer = HomeReducer(),
                    actors = setOf(
                        ConnectWalletActor(walletRepository = get()),
                        ObserveWalletActor(walletRepository = get()),
                        LoadMarketsActor(getMarketsInteractor = get()),
                    ),
                )
            }
        }
    }
} 