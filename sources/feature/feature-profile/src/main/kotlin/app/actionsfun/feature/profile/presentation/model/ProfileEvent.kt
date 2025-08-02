package app.actionsfun.feature.profile.presentation.model

import app.actionsfun.feature.profile.domain.MarketWithClaimStatus

internal sealed interface ProfileEvent {
    
    data object Initialize : ProfileEvent
    
    data class MarketsLoaded(
        val markets: List<MarketWithClaimStatus>,
        val publicKey: String?,
    ) : ProfileEvent
    
    data class MarketsLoadingError(
        val error: Throwable,
    ) : ProfileEvent

    data class ConnectedWalletChanged(
        val userId: String,
        val publicKey: String?,
    ) : ProfileEvent

    data object EmptyEvent : ProfileEvent

    data class ConnectWalletFailed(
        val error: Throwable,
    ) : ProfileEvent

    data class SolClaimed(
        val marketAddress: String,
        val transactionSignature: String,
    ) : ProfileEvent

    data class SolClaimFailed(
        val marketAddress: String,
        val error: Throwable,
    ) : ProfileEvent
    
    data object WalletDisconnected : ProfileEvent
}

internal sealed interface ProfileUIEvent : ProfileEvent {
    
    data object PublicKeyClicked : ProfileUIEvent
    
    data object ShowWalletOptionsSheet : ProfileUIEvent
    
    data object DismissWalletOptionsSheet : ProfileUIEvent
    
    data object DisconnectWalletClicked : ProfileUIEvent
    
    data object CopyAddressClicked : ProfileUIEvent

    data class ClaimClicked(
        val marketAddress: String
    ) : ProfileUIEvent

    data object ConnectWalletClick : ProfileUIEvent

    data object RetryLoadMarketsClick : ProfileUIEvent
}
