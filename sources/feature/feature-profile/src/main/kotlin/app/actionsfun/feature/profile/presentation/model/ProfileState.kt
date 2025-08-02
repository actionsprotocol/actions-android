package app.actionsfun.feature.profile.presentation.model

import app.actionsfun.feature.profile.domain.MarketWithClaimStatus

internal data class ProfileState(
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val publicKey: String? = null,
    val marketsWithClaimStatuses: List<MarketWithClaimStatus> = emptyList(),
    val showWalletOptionsSheet: Boolean = false,
) {
    
    companion object {
        
        val Default = ProfileState()
    }
}
