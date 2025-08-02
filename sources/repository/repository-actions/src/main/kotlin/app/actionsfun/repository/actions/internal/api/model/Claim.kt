package app.actionsfun.repository.actions.internal.api.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
internal data class ClaimData(
    val canClaim: Boolean,
    val alreadyClaimed: Boolean,
    val claimableAmount: String,
    val claimableAmountSol: Float,
    val multiplier: Float,
    val userBet: String,
    val userBetSol: Float,
    val userOption: Boolean,
    val winningOption: Boolean,
    val poolStatus: MarketStatus? = null,
    val reason: String? = null
)

@Keep
@Serializable
internal data class GetClaimResponse(
    val success: Boolean,
    val status: Int,
    val error: String? = null,
    val message: String? = null,
    val data: ClaimData? = null
)