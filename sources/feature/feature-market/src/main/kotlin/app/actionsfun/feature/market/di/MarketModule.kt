package app.actionsfun.feature.market.di

import app.actionsfun.feature.market.domain.GetMarketWithClaimStatus
import app.actionsfun.feature.market.presentation.MarketReducer
import app.actionsfun.feature.market.presentation.MarketStore
import app.actionsfun.feature.market.presentation.MarketStoreProvider
import app.actionsfun.feature.market.presentation.MarketUIStateMapper
import app.actionsfun.feature.market.presentation.actor.ClaimSolActor
import app.actionsfun.feature.market.presentation.actor.ConnectWalletActor
import app.actionsfun.feature.market.presentation.actor.DepositSolActor
import app.actionsfun.feature.market.presentation.actor.GetBalanceActor
import app.actionsfun.feature.market.presentation.actor.LoadMarketActor
import app.actionsfun.feature.market.presentation.actor.ObserveNetworkStateActor
import app.actionsfun.feature.market.presentation.actor.ObserveWalletStateActor
import app.actionsfun.feature.market.presentation.actor.SendReplyActor
import app.actionsfun.feature.market.presentation.model.MarketEvent
import app.actionsfun.feature.market.presentation.model.MarketState
import org.koin.dsl.module

val MarketModule = module {
    factory<MarketStoreProvider> {
        object : MarketStoreProvider() {
            override fun provide(marketAddress: String): MarketStore {
                return MarketStore(
                    initialEvents = listOf(
                        MarketEvent.LoadMarket(marketAddress),
                        MarketEvent.ObserveNetwork,
                    ),
                    initialState = MarketState(),
                    actors = setOf(
                        LoadMarketActor(GetMarketWithClaimStatus(get(), get())),
                        ObserveNetworkStateActor(get()),
                        ObserveWalletStateActor(get()),
                        GetBalanceActor(get()),
                        DepositSolActor(get()),
                        ClaimSolActor(get()),
                        SendReplyActor(get()),
                        ConnectWalletActor(get()),
                    ),
                    uiStateMapper = MarketUIStateMapper(),
                    reducer = MarketReducer(marketAddress),
                )
            }
        }
    }
} 