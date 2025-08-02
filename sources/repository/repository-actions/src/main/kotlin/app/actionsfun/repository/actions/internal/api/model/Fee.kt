package app.actionsfun.repository.actions.internal.api.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
internal data class CreatorFeesData(
    val marketId: String,
    val creatorFeeAccountPda: String,
    val accumulatedFees: String,
    val accumulatedFeesSol: String,
    val accountExists: Boolean
)

@Keep
@Serializable
internal data class GetCreatorFeesResponse(
    val success: Boolean,
    val status: Int,
    val error: String? = null,
    val message: String? = null,
    val data: CreatorFeesData? = null
)
