package app.actionsfun.repository.solana.internal.core.rpc

import kotlinx.serialization.Serializable

@Serializable
internal data class GetTokenApplyResponse(
    val value: TokenAmount,
)

@Serializable
data class TokenAmount(
    val amount: Long,
    val decimals: Int,
    val uiAmountString: String,
)
