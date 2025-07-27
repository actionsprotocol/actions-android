package app.actionsfun.repository.actions.internal.api.model

import kotlinx.serialization.Serializable

@Serializable
internal data class ClaimData(
    val canClaim: Boolean,
    val alreadyClaimed: Boolean,
    val claimableAmount: String,
    val claimableAmountSol: Double,
    val multiplier: Double,
    val userBet: String,
    val userBetSol: Double,
    val userOption: Boolean,
    val winningOption: Boolean,
    val poolStatus: MarketStatus? = null,
    val reason: String? = null
)

@Serializable
internal data class GetClaimResponse(
    val success: Boolean,
    val status: Int,
    val error: String? = null,
    val message: String? = null,
    val data: ClaimData? = null
)