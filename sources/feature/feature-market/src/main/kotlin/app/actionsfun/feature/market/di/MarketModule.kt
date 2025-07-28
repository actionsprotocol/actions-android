package app.actionsfun.feature.market.di

import app.actionsfun.feature.market.presentation.MarketReducer
import app.actionsfun.feature.market.presentation.MarketStore
import app.actionsfun.feature.market.presentation.MarketStoreProvider
import app.actionsfun.feature.market.presentation.MarketUIStateMapper
import app.actionsfun.feature.market.presentation.model.MarketState
import org.koin.dsl.module

val MarketModule = module {
    factory<MarketStoreProvider> {
        object : MarketStoreProvider() {
            override fun provide(marketAddress: String): MarketStore {
                return MarketStore(
                    initialEvents = listOf(),
                    initialState = MarketState,
                    actors = setOf(
                        // Add actors here
                    ),
                    uiStateMapper = MarketUIStateMapper(),
                    reducer = MarketReducer(),
                )
            }
        }
    }
} 