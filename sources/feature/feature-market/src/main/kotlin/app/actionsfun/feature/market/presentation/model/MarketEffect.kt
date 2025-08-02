package app.actionsfun.feature.market.presentation.model

internal sealed interface MarketEffect {

    data class ShowErrorToast(val message: String) : MarketEffect

    data class ShowSuccessToast(val message: String) : MarketEffect
}