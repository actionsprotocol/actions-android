package app.actionsfun.feature.profile.presentation.model

internal sealed interface ProfileCommand {

    data object ObserveMarkets : ProfileCommand

    data object ObserveWallet : ProfileCommand

    data object ConnectWallet : ProfileCommand
    
    data object DisconnectWallet : ProfileCommand

    data object LoadMarkets : ProfileCommand

    data class ClaimSol(val marketAddress: String) : ProfileCommand
}