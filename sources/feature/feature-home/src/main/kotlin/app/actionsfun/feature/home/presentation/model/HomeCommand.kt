package app.actionsfun.feature.home.presentation.model

internal sealed interface HomeCommand {

    data object ObserveWallet : HomeCommand

    data object LoadMarkets : HomeCommand

    data object ConnectWallet : HomeCommand
}
