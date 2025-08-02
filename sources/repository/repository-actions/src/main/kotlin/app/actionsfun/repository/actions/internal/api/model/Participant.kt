package app.actionsfun.repository.actions.internal.api.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
internal data class GetParticipantsResponse(
    val success: Boolean,
    val status: Int,
    val error: String? = null,
    val message: String? = null,
    val data: List<SerializedParticipant>? = null
)

@Keep
@Serializable
internal data class SerializedParticipant(
    val publicKey: String,
    val amount: String,
    val option: Boolean,
    val timestamp: Long
)

@Keep
@Serializable
internal data class ParticipantInfo(
    val amount: String,
    val option: Boolean,
    val predictionTimestamp: Long
)