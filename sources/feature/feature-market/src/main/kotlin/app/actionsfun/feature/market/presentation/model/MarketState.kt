package app.actionsfun.feature.market.presentation.model

import app.actionsfun.feature.market.domain.MarketWithClaimStatus

internal data class MarketState(
    val market: MarketWithClaimStatus? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val deposit: DepositState = DepositState(),
    val balanceSol: Float = 0f,
    val walletPublicKey: String? = null,
)

internal data class DepositState(
    val enabled: Boolean = false,
    val option: Boolean = true,
    val amount: Float = 0f,
    val balance: Float = 0f,
    val loading: Boolean = false,
)