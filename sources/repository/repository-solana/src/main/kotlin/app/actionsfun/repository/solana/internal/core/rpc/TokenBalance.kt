package app.actionsfun.repository.solana.internal.core.rpc

import kotlinx.serialization.Serializable

@Serializable
internal data class TokenBalanceResult(val value: TokenBalanceValue)

@Serializable
internal data class TokenBalanceValue(
    val amount: String,
    val decimals: Int,
    val uiAmountString: String,
)
