package app.actionsfun.feature.market.presentation.model

internal sealed interface MarketEvent {
    // Add domain events here
}

internal sealed interface MarketUIEvent : MarketEvent {
    // Add UI events here
} 